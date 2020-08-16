package mf.patomessenger.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import mf.patomessenger.androidServices.PushService
import mf.patomessenger.presentation.base.BaseViewModel
import mf.patomessenger.presentation.stateModel.MainState
import mf.patomessenger.useCases.auth.IAuthUseCase
import mf.patomessenger.useCases.conversations.IConversationsUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val _authUseCase: IAuthUseCase,
    private val _conversationsUseCase: IConversationsUseCase
) : BaseViewModel() {
    private val _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState> = _mainState

    fun getCurrentUser() = _authUseCase.getCurrentUser()

    fun signOut() = ioContext.launch {
        _mainState.value = MainState(uiBlocked = true)
        _authUseCase.signOut()
        _mainState.value = MainState(loggedOut = true)
    }

    fun unsubscribeTopics() = _conversationsUseCase.unsubscribeTopics()
}