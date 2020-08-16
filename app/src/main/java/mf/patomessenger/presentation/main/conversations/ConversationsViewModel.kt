package mf.patomessenger.presentation.main.conversations

import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.collect
import mf.patomessenger.presentation.base.BaseViewModel
import mf.patomessenger.useCases.conversations.IConversationsUseCase
import javax.inject.Inject

class ConversationsViewModel @Inject constructor(
    private val _conversationsUseCase: IConversationsUseCase
) : BaseViewModel() {

    val conversations = liveData {
        _conversationsUseCase.observeConversations().collect {
            emit(it)

        }
    }
}