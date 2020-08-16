package mf.patomessenger.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import mf.patomessenger.presentation.auth.AuthActivity
import mf.patomessenger.presentation.main.MainActivity

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}