package mf.patomessenger.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import mf.patomessenger.androidServices.PushService

@Module
abstract class ServiceModule {
    @ContributesAndroidInjector
    abstract fun messagingService() : PushService
}