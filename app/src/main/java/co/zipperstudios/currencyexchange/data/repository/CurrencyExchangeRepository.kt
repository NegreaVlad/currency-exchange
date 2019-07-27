package co.zipperstudios.currencyexchange.data.repository

import androidx.lifecycle.LiveData
import co.zipperstudios.currencyexchange.api.ApiResponse
import co.zipperstudios.currencyexchange.api.CurrencyExchangeApi
import co.zipperstudios.currencyexchange.api.NetworkOnlyResource
import co.zipperstudios.currencyexchange.api.Resource
import co.zipperstudios.currencyexchange.utils.AppExecutors
import co.zipperstudios.currencyexchange.api.providers.CurrencyExchangeProvider
import co.zipperstudios.currencyexchange.data.model.CurrencyExchangeResponse
import javax.inject.Inject

class CurrencyExchangeRepository @Inject constructor(
    private val currencyExchangeApi: CurrencyExchangeApi,
    private val appExecutors: AppExecutors
) : CurrencyExchangeProvider {
    override fun getExchangeRates(currencyCountryCode: String): LiveData<Resource<CurrencyExchangeResponse>> =
        object : NetworkOnlyResource<CurrencyExchangeResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<CurrencyExchangeResponse>> =
                currencyExchangeApi.getExchangeRates(currencyCountryCode)
        }.asLiveData()
}