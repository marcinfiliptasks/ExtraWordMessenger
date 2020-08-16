package mf.patomessenger.presentation.stateModel

data class BasicFormState(
    val isDataValid: Boolean = false,
    val uiBlocked: Boolean = false,
    val allDone: Boolean = false
)