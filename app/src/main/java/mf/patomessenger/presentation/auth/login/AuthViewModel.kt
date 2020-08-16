package mf.patomessenger.presentation.auth.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mf.patomessenger.R
import mf.patomessenger.androidServices.PushService
import mf.patomessenger.model.LoggedInUser
import mf.patomessenger.presentation.stateModel.LoginFormState
import mf.patomessenger.model.LoginResult
import mf.patomessenger.model.Result
import mf.patomessenger.useCases.auth.IAuthUseCase
import mf.patomessenger.presentation.base.BaseViewModel
import mf.patomessenger.useCases.conversations.IConversationsUseCase
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val _authUseCase: IAuthUseCase,
    private val _conversationsUseCase: IConversationsUseCase
) : BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) = ioContext.launch {
        _loginForm.value = LoginFormState(uiBlocked = true)
        _loginResult.value = _authUseCase.login(username, password).processResult()
    }


    private fun isUserNameValid(username: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(username).matches()

    fun loginDataChanged(username: String, password: String) {
        _loginForm.value = if (!isUserNameValid(username)) {
            LoginFormState(usernameError = R.string.invalid_mail)
        } else if (!isPasswordValid(password)) {
            LoginFormState(passwordError = R.string.invalid_password)
        } else {
            LoginFormState(isDataValid = true)
        }
    }

    private fun isPasswordValid(password: String): Boolean = password.length > 5

    private  fun Result<LoggedInUser>.processResult() =
        when (this) {
            is Result.Success -> {
                sendToken()
                LoginResult(success = this.data.displayName)

            }
            is Result.Error -> {
                errorMessageMutable.value = this.exception.message
                _loginForm.value = LoginFormState(isDataValid = true)
                LoginResult(error = R.string.login_failed)
            }
        }

    fun register(userName: String, password: String) = ioContext.launch {
        _loginForm.value = LoginFormState(uiBlocked = true)
        _authUseCase.register(userName, password).processResult().also {
            if (it.error != null) {
                loginDataChanged(userName, password)
            }else{
                checkIfIsAuth()
                loginDataChanged(userName, password)
            }
        }
    }

    init {
        checkIfIsAuth()
    }

    private fun checkIfIsAuth() = ioContext.launch {
        _authUseCase.getCurrentUser()?.let {
            sendToken()
            _loginResult.value = LoginResult(success = it.displayName)
        }
    }

    private fun sendToken() = ioContext.launch {
        FirebaseInstanceId.getInstance().instanceId.await()?.let {
            _conversationsUseCase.updateToken(it.token)
        }
    }
}