package mf.patomessenger.presentation.splash

import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import mf.patomessenger.presentation.base.BaseViewModel
import mf.patomessenger.useCases.auth.IAuthUseCase
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
    private val _authUseCase: IAuthUseCase
) : BaseViewModel() {
    val authState = liveData {
        delay(1000)
        emit(_authUseCase.getCurrentUser() != null)
    }
}