package co.zipperstudios.currencyexchange.data.model

data class CurrencyExchangeResponse(
    val base: String,
    val date: String,
    var exchangeRates: Set<CurrencyExchange>
)