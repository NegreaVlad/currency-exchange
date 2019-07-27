package co.zipperstudios.currencyexchange.data.repository

import androidx.lifecycle.LiveData
import co.zipperstudios.currencyexchange.api.ApiResponse
import co.zipperstudios.currencyexchange.api.CurrencyExchangeApi
import co.zipperstudios.currencyexchange.api.NetworkOnlyResource
import co.zipperstudios.currencyexchange.api.Resource
import co.zipperstudios.currencyexchange.api.utils.AppExecutors
import com.ewsgroup.mscrew.api.providers.CurrencyExchangeProvider
import javax.inject.Inject

class CurrencyExchangeRepository @Inject constructor(
    private val currencyExchangeApi: CurrencyExchangeApi,
    private val appExecutors: AppExecutors
) : CurrencyExchangeProvider {
    override fun getExchangeRates(currencyCountryCode: String): LiveData<Resource<Any>> =
        object : NetworkOnlyResource<Any>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<Any>> =
                currencyExchangeApi.getExchangeRates(currencyCountryCode)
        }.asLiveData()
}