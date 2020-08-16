package mf.patomessenger.presentation.stateModel

data class MainState(
    val uiBlocked: Boolean = false,
    val loggedOut: Boolean = false
)