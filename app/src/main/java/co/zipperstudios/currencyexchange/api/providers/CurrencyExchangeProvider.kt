package com.ewsgroup.mscrew.api.providers

import androidx.lifecycle.LiveData
import co.zipperstudios.currencyexchange.api.Resource

interface CurrencyExchangeProvider {
    fun getExchangeRates(currencyCountryCode: String): LiveData<Resource<Any>>
}