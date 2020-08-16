package mf.patomessenger.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import mf.patomessenger.configurations.GROUPS_ID
import mf.patomessenger.configurations.MESSAGES_COLLECTION
import mf.patomessenger.model.MessageModel
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MessagesRepository @Inject constructor(
    private val store: FirebaseFirestore
) : IMessagesRepository {

    override fun observeMessages(conversationId: String): Flow<List<MessageModel>> = callbackFlow {
        store.collection(MESSAGES_COLLECTION)
            .whereEqualTo(GROUPS_ID, conversationId)
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    offer(it.toObjects(MessageModel::class.java))
                }
            }.also {
                awaitClose { it.remove() }
            }
    }

    override suspend fun sendMessage(message: MessageModel) {
        store.collection(MESSAGES_COLLECTION)
            .add(message)
            .await()
    }
}