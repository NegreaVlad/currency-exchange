package co.zipperstudios.currencyexchange.utils

import co.zipperstudios.currencyexchange.data.model.CurrencyExchange
import co.zipperstudios.currencyexchange.data.model.CurrencyExchangeResponse
import com.google.gson.*

import java.lang.reflect.Type
import java.util.HashMap

class CurrencyExchangeDeserializer : JsonDeserializer<CurrencyExchangeResponse> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CurrencyExchangeResponse {
        val jsonObject = json.asJsonObject

        // Read simple String values.
        val uri = jsonObject.get(KEY_BASE).asString
        val httpMethod = jsonObject.get(KEY_DATE).asString

        // Read the dynamic exchangeRates object.
        val exchangeRates = readExchangeRates(jsonObject)

        return CurrencyExchangeResponse(uri, httpMethod, exchangeRates!!)
    }

    private fun readParametersMap(jsonObject: JsonObject): Map<String, Float>? {
        val paramsElement = jsonObject.get(KEY_RATES)
            ?: // value not present at all, just return null
            return null

        val parametersObject = paramsElement.asJsonObject

        val parameters = HashMap<String, Float>()
        for ((key, value1) in parametersObject.entrySet()) {
            val value = value1.asFloat
            parameters[key] = value
        }


        return parameters
    }


    private fun readExchangeRates(jsonObject: JsonObject): HashSet<CurrencyExchange>? {
        val paramsElement = jsonObject.get(KEY_RATES)
            ?: // value not present at all, just return null
            return null

        val parametersObject = paramsElement.asJsonObject

        val exchangeRates = HashSet<CurrencyExchange>()

        for ((key, value1) in parametersObject.entrySet()) {
            exchangeRates.add(CurrencyExchange(key, value1.asFloat))
        }

        return exchangeRates
    }

    companion object {

        private val KEY_BASE = "base"
        private val KEY_DATE = "date"
        private val KEY_RATES = "rates"
    }
}
