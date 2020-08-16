package mf.patomessenger.presentation.main.messages

import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import mf.patomessenger.androidServices.PushService
import mf.patomessenger.presentation.base.BaseViewModel
import mf.patomessenger.useCases.messages.IMessagesUseCase
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class MessagesViewModel @Inject constructor(private val _messagesUseCase: IMessagesUseCase) :
    BaseViewModel() {
    private var _conversationId: MutableLiveData<String> = MutableLiveData()

    val messages = Transformations.switchMap(_conversationId) { id ->
        _messagesUseCase.observeMessages(id).map { message -> message.sortedBy { it.sendDate } }
            .asLiveData()
    }

    fun setConversationId(conversationId: String) {
        _conversationId.value = conversationId
    }

    fun sendMessage(conversationId: String, message: String) = ioContext.launch {
        if (message.isNotEmpty())
            _messagesUseCase.sendMessage(conversationId, message)
    }

    fun setPushInfo(conversationId: String) {
        PushService.selectedConversation = conversationId
    }
}