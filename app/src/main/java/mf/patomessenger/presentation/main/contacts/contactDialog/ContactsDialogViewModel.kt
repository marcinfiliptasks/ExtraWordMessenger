package mf.patomessenger.presentation.main.contacts.contactDialog

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import mf.patomessenger.model.ContactModel
import mf.patomessenger.model.Result
import mf.patomessenger.presentation.base.BaseViewModel
import mf.patomessenger.presentation.stateModel.BasicFormState
import mf.patomessenger.useCases.auth.IAuthUseCase
import mf.patomessenger.useCases.contacts.IContactDialogUseCase
import javax.inject.Inject

class ContactsDialogViewModel @Inject constructor(
    private val _contactDialogUseCase: IContactDialogUseCase,
    private val _authUseCase: IAuthUseCase
) : BaseViewModel() {
    private var _basicState: MutableLiveData<BasicFormState> = MutableLiveData()
    val basicState: LiveData<BasicFormState> = _basicState

    fun addContact(userName: String) = ioContext.launch {
        _basicState.value = BasicFormState(uiBlocked = true)
        _contactDialogUseCase.addContactByMail(userName).processResult()
    }.invokeOnCompletion {
        _basicState.value = BasicFormState(uiBlocked = false, isDataValid = true)
    }

    private fun Result<ContactModel>.processResult() {
        if (this is Result.Success) {
            _basicState.postValue(
                BasicFormState(
                    uiBlocked = false,
                    allDone = true,
                    isDataValid = true
                )
            )
        } else {
            errorMessageMutable.value = "User not found!"
        }
    }

    fun checkMail(username: String) {
        _basicState.value = (BasicFormState(
                isDataValid = Patterns.EMAIL_ADDRESS.matcher(username).matches() &&
                        !username.equals(_authUseCase.getCurrentUser()?.displayName, ignoreCase = true)
        ))

    }


}


