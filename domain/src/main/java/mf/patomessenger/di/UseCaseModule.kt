package mf.patomessenger.di

import dagger.Binds
import dagger.Module
import mf.patomessenger.useCases.auth.IAuthUseCase
import mf.patomessenger.useCases.auth.AuthUseCase
import mf.patomessenger.useCases.contacts.IContactDialogUseCase
import mf.patomessenger.useCases.contacts.ContactDialogUseCase
import mf.patomessenger.useCases.contacts.IContactsUseCase
import mf.patomessenger.useCases.contacts.ContactsUseCase
import mf.patomessenger.useCases.conversations.IConversationsUseCase
import mf.patomessenger.useCases.conversations.ConversationsUseCase
import mf.patomessenger.useCases.messages.IMessagesUseCase
import mf.patomessenger.useCases.messages.MessagesUseCase
import javax.inject.Singleton

@Module
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun authUseCase(authUseCase: AuthUseCase)
            : IAuthUseCase

    @Binds
    @Singleton
    abstract fun conversationsUseCase(messagingUseCase: ConversationsUseCase)
            : IConversationsUseCase

    @Binds
    @Singleton
    abstract fun contactsUseCase(contactsUseCase: ContactsUseCase)
            : IContactsUseCase

    @Binds
    @Singleton
    abstract fun contactDialogUseCase(contactDialogUseCase: ContactDialogUseCase)
            : IContactDialogUseCase

    @Binds
    @Singleton
    abstract fun messagesUseCase(messagesUseCase: MessagesUseCase)
            : IMessagesUseCase
}