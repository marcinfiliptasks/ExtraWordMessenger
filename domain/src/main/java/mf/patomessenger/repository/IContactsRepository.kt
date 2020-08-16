package mf.patomessenger.repository

import kotlinx.coroutines.flow.Flow
import mf.patomessenger.model.ContactModel
import mf.patomessenger.model.Result

interface IContactsRepository {
    suspend fun observeContacts(userId: String): Flow<List<ContactModel>>
    suspend fun addContactByMail(userName: String): Result<ContactModel>
}