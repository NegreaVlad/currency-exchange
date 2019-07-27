package co.zipperstudios.currencyexchange.di.modules

import co.zipperstudios.currencyexchange.ui.main.MainActivity
import co.zipperstudios.currencyexchange.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectorsModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindLoginActivity(): MainActivity

}