package co.zipperstudios.currencyexchange.ui.currency.exchange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import co.zipperstudios.currencyexchange.api.providers.CurrencyExchangeProvider
import co.zipperstudios.currencyexchange.data.model.CurrencyExchange
import co.zipperstudios.currencyexchange.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class CurrencyExchangeVM @Inject constructor(currencyExchangeProvider: CurrencyExchangeProvider) : BaseViewModel() {
    var currentBaseCurrency: String = "EUR"
    val refresh = MutableLiveData<Boolean>()

    val currencyExchanges = Transformations.switchMap(refresh) {
        Transformations.map(currencyExchangeProvider.getExchangeRates(currentBaseCurrency)) { item ->
            item.data?.apply {
                val exchanges = exchangeRates.toMutableList()

                val baseCurrency = CurrencyExchange(base, 1f, true)
                exchanges.add(baseCurrency)
                Collections.rotate(exchanges, 1)
                this.exchangeRates = exchanges.toSet()
            }
        }
    }
}