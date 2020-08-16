package mf.patomessenger.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun context(app: Application): Context =  app
}