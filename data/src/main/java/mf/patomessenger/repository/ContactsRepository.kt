package mf.patomessenger.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import mf.patomessenger.model.ContactModel
import mf.patomessenger.model.Result
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ContactsRepository @Inject constructor(
    private val store: FirebaseFirestore,
    private val cloudFunctions: FirebaseFunctions
) : IContactsRepository {

    override suspend fun observeContacts(userId: String): Flow<List<ContactModel>> = callbackFlow {
            store.collection("users")
                .document(userId)
                .collection("contacts")
                .addSnapshotListener { snapshot, _ ->
                    snapshot?.let {
                        offer(it.toObjects(ContactModel::class.java))
                    }
                }.also {
                    awaitClose { it.remove() }
                }
    }

    override suspend fun addContactByMail(userName: String): Result<ContactModel> =
        cloudFunctions.getHttpsCallable("getUserIdByMail")
            .call(HashMap<String, String>().apply {
                put("mail", userName)
                put("uuid", UUID.randomUUID().toString())
            }).await()
            .toResult()

    private fun HttpsCallableResult.toResult(): Result<ContactModel> =
        data?.toString()?.let {
            Result.Success(ContactModel(id = it))
        } ?: Result.Error(Exception("User not found!"))
}












