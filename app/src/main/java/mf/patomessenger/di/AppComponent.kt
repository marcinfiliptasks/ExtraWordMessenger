package mf.patomessenger.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import mf.patomessenger.AppMain
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        AppModule::class,
        ViewModelModule::class,
        FragmentBuilderModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
        DatabaseModule::class,
        ServiceModule::class
    ]
)

interface AppComponent : AndroidInjector<AppMain> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
