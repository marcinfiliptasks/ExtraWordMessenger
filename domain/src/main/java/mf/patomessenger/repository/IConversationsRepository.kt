package mf.patomessenger.repository

import kotlinx.coroutines.flow.Flow
import mf.patomessenger.model.ConversationModel
import mf.patomessenger.model.UserTokenData

interface IConversationsRepository {
    suspend fun updateToken(userId: String, tokenData: UserTokenData)
    fun observeConversations(userId: String) : Flow<List<ConversationModel>>
    fun subscribeTopics(topics: List<String>)
    fun unsubscribeTopics()
}