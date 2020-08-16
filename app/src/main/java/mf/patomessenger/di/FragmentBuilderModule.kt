package mf.patomessenger.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import mf.patomessenger.presentation.auth.login.AuthFragment
import mf.patomessenger.presentation.main.contacts.ContactsFragment
import mf.patomessenger.presentation.main.conversations.ConversationsFragment
import mf.patomessenger.presentation.main.contacts.contactDialog.ContactsDialogFragment
import mf.patomessenger.presentation.main.messages.MessagesFragment
import mf.patomessenger.presentation.splash.SplashScreenFragment

@FlowPreview
@ExperimentalCoroutinesApi
@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeAuthFragment(): AuthFragment

    @ContributesAndroidInjector
    abstract fun contributeConversationsFragment(): ConversationsFragment

    @ContributesAndroidInjector
    abstract fun contributeContactsFragment(): ContactsFragment

    @ContributesAndroidInjector
    abstract fun contributeContactDialog(): ContactsDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeMessagesFragment(): MessagesFragment

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashScreenFragment
}