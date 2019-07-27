package co.zipperstudios.currencyexchange.di.modules

import co.zipperstudios.currencyexchange.api.CurrencyExchangeApi
import co.zipperstudios.currencyexchange.utils.AppExecutors
import co.zipperstudios.currencyexchange.data.repository.CurrencyExchangeRepository
import co.zipperstudios.currencyexchange.api.providers.CurrencyExchangeProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideJobsRepository(
        currencyExchangeApi: CurrencyExchangeApi,
        appExecutors: AppExecutors
    ): CurrencyExchangeProvider {
        return CurrencyExchangeRepository(currencyExchangeApi, appExecutors)
    }
}
