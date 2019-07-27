package co.zipperstudios.currencyexchange.api.module

import co.zipperstudios.currencyexchange.BuildConfig
import co.zipperstudios.currencyexchange.api.CurrencyExchangeApi
import co.zipperstudios.currencyexchange.data.model.CurrencyExchangeResponse
import co.zipperstudios.currencyexchange.utils.LiveDataCallAdapterFactory
import co.zipperstudios.currencyexchange.utils.CurrencyExchangeDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
internal class ApiModule {

    @Provides
    @Singleton
    @Named("url")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    @Named("unauthorizedOkhttp")
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
            connectTimeout(BuildConfig.TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Provides
    @Singleton
    @Named("unauthorizedRetrofit")
    fun provideRetrofit(
        @Named("url") baseUrl: String, @Named("unauthorizedOkhttp") client: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun getConverterFactory(gson: Gson) = GsonConverterFactory.create(gson)

    @Provides
    fun getGsonConverter(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(CurrencyExchangeResponse::class.javaObjectType,
            CurrencyExchangeDeserializer()
        )
        return builder.create()
    }

    @Provides
    @Singleton
    @Named("unauthorizedLiveDataRetrofit")
    fun provideAuthorizedLiveDataRetrofit(
        @Named("url") baseUrl: String,
        @Named("unauthorizedOkhttp") client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyExchangeApi(@Named("unauthorizedLiveDataRetrofit") retrofit: Retrofit): CurrencyExchangeApi {
        return retrofit.create(CurrencyExchangeApi::class.java)
    }
}