package mf.patomessenger.useCases.messages

import kotlinx.coroutines.flow.Flow
import mf.patomessenger.configurations.EXTRA_WORD
import mf.patomessenger.configurations.EXTRA_WORD_CHANCE
import mf.patomessenger.configurations.EXTRA_WORD_SPACING
import mf.patomessenger.model.MessageModel
import mf.patomessenger.model.exceptions.UnauthorizedException
import mf.patomessenger.repository.IAuthRepository
import mf.patomessenger.repository.IMessagesRepository
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class MessagesUseCase @Inject constructor(
    private val _messagesRepository: IMessagesRepository,
    private val _authRepository: IAuthRepository
) : IMessagesUseCase {

    override fun observeMessages(conversationId: String): Flow<List<MessageModel>> =
        _messagesRepository.observeMessages(conversationId)

    override suspend fun sendMessage(conversationId: String, message: String) =
        _messagesRepository.sendMessage(
            message = buildMessageModel(message.appendExtraWord(), conversationId)
        )

    private fun buildMessageModel(message: String, conversationId: String) =
        _authRepository.getCurrentUser()?.let {
            MessageModel(
                it.userId,
                message,
                conversationId,
                sendDate = Timestamp(Calendar.getInstance().timeInMillis),
                senderName = it.displayName
            )
        } ?: throw UnauthorizedException()

    fun String.appendExtraWord(): String =
        split(" ").toMutableList().let { splittedText ->
            var loopIndex = 0
            while (loopIndex < splittedText.size) {
                if (splittedText[loopIndex].equals(EXTRA_WORD, true)) {
                    loopIndex += EXTRA_WORD_SPACING
                    continue
                }
                if (Random.nextInt(0, EXTRA_WORD_CHANCE)
                    ==
                    Random.nextInt(0, EXTRA_WORD_CHANCE)
                ) {
                    splittedText.add(loopIndex + 1, EXTRA_WORD)
                    loopIndex += EXTRA_WORD_SPACING + 1
                    continue
                }
                loopIndex++
            }
            splittedText.joinToString(separator = " ")
        }
}




