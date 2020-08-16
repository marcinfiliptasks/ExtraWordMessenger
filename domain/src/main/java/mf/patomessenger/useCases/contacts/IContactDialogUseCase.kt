package mf.patomessenger.useCases.contacts

import mf.patomessenger.model.ContactModel
import mf.patomessenger.model.Result

interface IContactDialogUseCase {
    suspend fun addContactByMail(userName: String) : Result<ContactModel>
}