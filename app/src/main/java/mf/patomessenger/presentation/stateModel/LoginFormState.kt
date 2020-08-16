package mf.patomessenger.presentation.stateModel


data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false,
    val uiBlocked: Boolean = false
)