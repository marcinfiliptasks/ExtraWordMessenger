package mf.patomessenger.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import mf.patomessenger.presentation.auth.login.AuthViewModel
import mf.patomessenger.presentation.main.contacts.ContactsViewModel
import mf.patomessenger.presentation.main.conversations.ConversationsViewModel
import mf.patomessenger.presentation.main.contacts.contactDialog.ContactsDialogViewModel
import mf.patomessenger.presentation.main.MainViewModel
import mf.patomessenger.presentation.main.messages.MessagesViewModel
import mf.patomessenger.presentation.splash.SplashScreenViewModel
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindLoginViewModel(fragmentViewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConversationsViewModel::class)
    abstract fun bindConversationsViewModel(fragmentViewModel: ConversationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(fragmentViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    abstract fun bindContactsViewModel(fragmentViewModel: ContactsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactsDialogViewModel::class)
    abstract fun bindContactDialogViewModel(fragmentViewModel: ContactsDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    abstract fun bindMessagesViewModel(messagesViewModel: MessagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    abstract fun bindSplashViewModel(messagesViewModel: SplashScreenViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)