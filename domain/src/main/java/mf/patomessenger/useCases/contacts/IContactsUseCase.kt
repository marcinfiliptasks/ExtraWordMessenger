package mf.patomessenger.useCases.contacts

import kotlinx.coroutines.flow.Flow
import mf.patomessenger.model.ContactModel

interface IContactsUseCase {
    suspend fun observeContacts() : Flow<List<ContactModel>>
}