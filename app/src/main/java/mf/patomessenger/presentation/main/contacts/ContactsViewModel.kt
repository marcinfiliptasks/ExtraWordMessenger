package mf.patomessenger.presentation.main.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.collect
import mf.patomessenger.model.ContactModel
import mf.patomessenger.presentation.base.BaseViewModel
import mf.patomessenger.presentation.stateModel.BasicFormState
import mf.patomessenger.useCases.contacts.IContactsUseCase
import javax.inject.Inject

class ContactsViewModel @Inject constructor(private val _contactsUseCase: IContactsUseCase) :
    BaseViewModel() {

    private var _basicState: MutableLiveData<BasicFormState> = MutableLiveData()
    val basicState: LiveData<BasicFormState> = _basicState


    private val _conversations: MutableLiveData<List<ContactModel>> = MutableLiveData()
    val contacts = liveData {
        _contactsUseCase.observeContacts().collect {
            emit(it)
        }
    }

}