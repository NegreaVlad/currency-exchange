package co.zipperstudios.currencyexchange.ui.currency.exchange

import dagger.Module
import dagger.Provides
import co.zipperstudios.currencyexchange.di.qualifiers.ViewModelInjection
import co.zipperstudios.currencyexchange.di.InjectionViewModelProvider

@Module
class CurrencyExchangeModule {

    @Provides
    @ViewModelInjection
    fun provideCurrencyExchangeVM(
        fragment: CurrencyExchangeFragment,
        viewModelProvider: InjectionViewModelProvider<CurrencyExchangeVM>
    ) = viewModelProvider.get(fragment, CurrencyExchangeVM::class)
}