package mf.patomessenger.di

import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mf.patomessenger.repository.*
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun loginRepository(loginRepository: AuthRepository): IAuthRepository

    @Binds
    @Singleton
    abstract fun conversationsRepository(conversationsRepository: ConversationsRepository): IConversationsRepository

    @ExperimentalCoroutinesApi
    @Binds
    @Singleton
    abstract fun contactRepository(contactRepository: ContactsRepository): IContactsRepository

    @Binds
    @Singleton
    abstract fun messagesRepository(messagesRepository: MessagesRepository): IMessagesRepository
}