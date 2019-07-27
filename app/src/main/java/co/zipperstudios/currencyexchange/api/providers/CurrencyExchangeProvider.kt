package co.zipperstudios.currencyexchange.api.providers

import androidx.lifecycle.LiveData
import co.zipperstudios.currencyexchange.api.Resource
import co.zipperstudios.currencyexchange.data.model.CurrencyExchangeResponse

interface CurrencyExchangeProvider {
    fun getExchangeRates(currencyCountryCode: String): LiveData<Resource<CurrencyExchangeResponse>>
}