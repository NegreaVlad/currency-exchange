package co.zipperstudios.currencyexchange.data.model

data class CurrencyExchange(
    val currencyCode: String,
    var exchangeRate: Float,
    var isHeader: Boolean
)