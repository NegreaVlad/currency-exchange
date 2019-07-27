package co.zipperstudios.currencyexchange.di.components

import co.zipperstudios.currencyexchange.App
import co.zipperstudios.currencyexchange.api.module.ApiModule
import co.zipperstudios.currencyexchange.di.modules.ActivityInjectorsModule
import co.zipperstudios.currencyexchange.di.modules.AppModule
import co.zipperstudios.currencyexchange.di.modules.FragmentInjectorsModule
import co.zipperstudios.currencyexchange.di.modules.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityInjectorsModule::class,
        FragmentInjectorsModule::class,
        RepositoryModule::class,
        AppModule::class,
        ApiModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}