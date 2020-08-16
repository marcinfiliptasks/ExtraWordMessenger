package mf.patomessenger.useCases.messages

import kotlinx.coroutines.flow.Flow
import mf.patomessenger.model.MessageModel

interface IMessagesUseCase {
    fun observeMessages(conversationId: String): Flow<List<MessageModel>>
    suspend fun sendMessage(conversationId: String, message: String)
}