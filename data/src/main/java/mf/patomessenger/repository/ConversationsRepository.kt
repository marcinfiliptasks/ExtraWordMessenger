package mf.patomessenger.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import mf.patomessenger.configurations.GROUPS_COLLECTION
import mf.patomessenger.configurations.GROUPS_KEYS_ARRAY
import mf.patomessenger.configurations.USERS_COLLECTION
import mf.patomessenger.model.ConversationModel
import mf.patomessenger.model.UserTokenData
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ConversationsRepository @Inject constructor(
    private val store: FirebaseFirestore,
    private val firebaseMessaging: FirebaseMessaging
) : IConversationsRepository {

    private val subscribedTopics = ArrayList<String>()

    override suspend fun updateToken(userId: String, tokenData: UserTokenData) {
        if (userId.isNotBlank()) {
            store.collection(USERS_COLLECTION)
                .document(userId)
                .set(tokenData)
                .await()
        }
    }

    override fun observeConversations(userId: String): Flow<List<ConversationModel>> =
        callbackFlow {
            store.collection(GROUPS_COLLECTION)
                .whereArrayContains(GROUPS_KEYS_ARRAY, userId)
                .addSnapshotListener { snapshot, _ ->
                    snapshot?.let {
                        offer(it.toObjects(ConversationModel::class.java).also { conversations ->
                            subscribeTopics(conversations.map { conversation -> conversation.id })
                        })
                    }
                }.also {
                    awaitClose { it.remove() }
                }
        }

    override fun subscribeTopics(topics: List<String>) =
        topics.filter { !subscribedTopics.contains(it) }.forEach {
            subscribedTopics.add(it)
            firebaseMessaging.subscribeToTopic(it)
        }

    override fun unsubscribeTopics() {
        subscribedTopics.forEach {
            firebaseMessaging.unsubscribeFromTopic(it)
        }
        subscribedTopics.clear()
    }
}