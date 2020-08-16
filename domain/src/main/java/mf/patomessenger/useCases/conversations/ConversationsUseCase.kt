package mf.patomessenger.useCases.conversations

import kotlinx.coroutines.flow.Flow
import mf.patomessenger.model.ConversationModel
import mf.patomessenger.model.UserTokenData
import mf.patomessenger.model.exceptions.UnauthorizedException
import mf.patomessenger.repository.IAuthRepository
import mf.patomessenger.repository.IConversationsRepository
import java.lang.Exception
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject

class ConversationsUseCase @Inject constructor(
    private val _conversationsRepo: IConversationsRepository,
    private val _authRepo: IAuthRepository
) : IConversationsUseCase {

    override suspend fun updateToken(token: String) = _conversationsRepo.updateToken(
        _authRepo.getCurrentUser()?.userId.orEmpty(),
        token.buildTokenData()
    )

    override fun observeConversations(): Flow<List<ConversationModel>> =
        _conversationsRepo.observeConversations(
            _authRepo.getCurrentUser()?.displayName ?: throw Exception(
                UnauthorizedException()
            )
        )

    override fun subscribeTopics(topics: List<String>) =
        _conversationsRepo.subscribeTopics(topics)

    override fun unsubscribeTopics() = _conversationsRepo.unsubscribeTopics()

    private fun String.buildTokenData() =
        UserTokenData(this, Timestamp(Calendar.getInstance().timeInMillis))
}