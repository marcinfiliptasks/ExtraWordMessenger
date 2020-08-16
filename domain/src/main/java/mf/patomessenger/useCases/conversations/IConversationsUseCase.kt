package mf.patomessenger.useCases.conversations

import kotlinx.coroutines.flow.Flow
import mf.patomessenger.model.ConversationModel

interface IConversationsUseCase {
    suspend fun updateToken(token: String)
    fun observeConversations() : Flow<List<ConversationModel>>
    fun subscribeTopics(topics: List<String>)
    fun unsubscribeTopics()
}