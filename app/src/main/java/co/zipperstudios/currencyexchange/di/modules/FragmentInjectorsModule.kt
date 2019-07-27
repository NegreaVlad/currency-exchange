package co.zipperstudios.currencyexchange.di.modules

import co.zipperstudios.currencyexchange.ui.currency.exchange.CurrencyExchangeFragment
import co.zipperstudios.currencyexchange.ui.currency.exchange.CurrencyExchangeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector(modules = [CurrencyExchangeModule::class])
    abstract fun currencyExchangeFragmentInjector(): CurrencyExchangeFragment
}