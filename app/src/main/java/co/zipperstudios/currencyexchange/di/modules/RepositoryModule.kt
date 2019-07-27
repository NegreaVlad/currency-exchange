package co.zipperstudios.currencyexchange.di.modules

import co.zipperstudios.currencyexchange.api.CurrencyExchangeApi
import co.zipperstudios.currencyexchange.api.utils.AppExecutors
import co.zipperstudios.currencyexchange.data.repository.CurrencyExchangeRepository
import com.ewsgroup.mscrew.api.providers.CurrencyExchangeProvider
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
