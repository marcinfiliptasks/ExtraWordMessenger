package mf.patomessenger.useCases.contacts

import mf.patomessenger.repository.IAuthRepository
import mf.patomessenger.repository.IContactsRepository
import javax.inject.Inject

class ContactsUseCase @Inject constructor(
    private val _contactsRepository: IContactsRepository,
    private val _authRepository: IAuthRepository
) : IContactsUseCase {

    override suspend fun observeContacts() =
        _contactsRepository.observeContacts(_authRepository.getCurrentUser()?.userId.orEmpty())
}