package co.zipperstudios.currencyexchange.api

import androidx.lifecycle.LiveData
import co.zipperstudios.currencyexchange.data.model.CurrencyExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyExchangeApi {

    @GET("/latest")
    fun getExchangeRates(@Query("base") currency: String): LiveData<ApiResponse<CurrencyExchangeResponse>>
}