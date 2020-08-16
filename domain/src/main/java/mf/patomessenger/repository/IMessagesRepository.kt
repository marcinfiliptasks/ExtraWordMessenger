package mf.patomessenger.repository

import kotlinx.coroutines.flow.Flow
import mf.patomessenger.model.MessageModel

interface IMessagesRepository {
    fun observeMessages(conversationId: String) : Flow<List<MessageModel>>
    suspend fun sendMessage(message: MessageModel)
}