package co.zipperstudios.currencyexchange.ui.currency.exchange

import javax.inject.Inject
import co.zipperstudios.currencyexchange.ui.base.BaseViewModel
import com.ewsgroup.mscrew.api.providers.CurrencyExchangeProvider

class CurrencyExchangeVM @Inject constructor(currencyExchangeProvider: CurrencyExchangeProvider) : BaseViewModel() {

    val currencyExchanges = currencyExchangeProvider.getExchangeRates("EUR")
}