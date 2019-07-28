package co.zipperstudios.currencyexchange.ui.currency.exchange

import androidx.lifecycle.Transformations
import co.zipperstudios.currencyexchange.api.providers.CurrencyExchangeProvider
import co.zipperstudios.currencyexchange.data.model.CurrencyExchange
import co.zipperstudios.currencyexchange.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class CurrencyExchangeVM @Inject constructor(currencyExchangeProvider: CurrencyExchangeProvider) : BaseViewModel() {

    val currencyExchanges =
        Transformations.map(currencyExchangeProvider.getExchangeRates("EUR")) { item ->
            item.data?.let { data ->
                val exchanges = data.exchangeRates.toMutableList()

                val baseCurrency = CurrencyExchange(data.base, 1f, true)
                exchanges.add(baseCurrency)
                Collections.rotate(exchanges, 1)
                return@map exchanges.toSet()
            }
        }
}