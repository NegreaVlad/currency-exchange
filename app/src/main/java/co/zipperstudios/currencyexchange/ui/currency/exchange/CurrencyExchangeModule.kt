package co.zipperstudios.currencyexchange.ui.currency.exchange

import dagger.Module
import dagger.Provides
import co.zipperstudios.currencyexchange.di.qualifiers.ViewModelInjection
import co.zipperstudios.currencyexchange.di.InjectionViewModelProvider
import com.ewsgroup.mscrew.api.providers.CurrencyExchangeProvider

@Module
class CurrencyExchangeModule {

    @Provides
    @ViewModelInjection
    fun provideCurrencyExchangeVM(
        fragment: CurrencyExchangeFragment,
        factory: CurrencyExchangeViewModelFactory,
        viewModelProvider: InjectionViewModelProvider<CurrencyExchangeVM>
    ) = viewModelProvider.get(fragment, CurrencyExchangeVM::class, factory)

    @Provides
    fun provideFactory(
        currencyExchangeProvider: CurrencyExchangeProvider
    ) = CurrencyExchangeViewModelFactory(currencyExchangeProvider)
}