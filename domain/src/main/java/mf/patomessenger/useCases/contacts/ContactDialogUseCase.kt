package mf.patomessenger.useCases.contacts

import mf.patomessenger.model.ContactModel
import mf.patomessenger.model.Result
import mf.patomessenger.repository.IContactsRepository
import javax.inject.Inject

class ContactDialogUseCase @Inject constructor(
    private val _contactsRepository: IContactsRepository
): IContactDialogUseCase {
    override suspend fun addContactByMail(userName: String): Result<ContactModel> =
        _contactsRepository.addContactByMail(
            userName
        )
}