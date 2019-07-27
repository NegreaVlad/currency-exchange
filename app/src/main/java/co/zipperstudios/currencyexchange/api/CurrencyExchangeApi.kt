package co.zipperstudios.currencyexchange.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface CurrencyExchangeApi {

    @GET("/latest")
    fun getExchangeRates(@Query("base") currency: String): LiveData<ApiResponse<Any>>
}