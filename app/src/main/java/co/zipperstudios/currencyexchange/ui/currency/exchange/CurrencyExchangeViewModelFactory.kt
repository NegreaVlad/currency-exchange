package co.zipperstudios.currencyexchange.ui.currency.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ewsgroup.mscrew.api.providers.CurrencyExchangeProvider

class CurrencyExchangeViewModelFactory(
    private val currencyExchangeProvider: CurrencyExchangeProvider
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyExchangeVM::class.java)) {

            return CurrencyExchangeVM(currencyExchangeProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}