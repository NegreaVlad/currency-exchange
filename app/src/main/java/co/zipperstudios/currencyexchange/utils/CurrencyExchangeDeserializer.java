package co.zipperstudios.currencyexchange.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import co.zipperstudios.currencyexchange.data.model.CurrencyExchangeResponse;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CurrencyExchangeDeserializer implements JsonDeserializer<CurrencyExchangeResponse> {

    private static final String KEY_URI = "base";
    private static final String KEY_METHOD = "date";
    private static final String KEY_PARAMETERS = "rates";

    @Override
    public CurrencyExchangeResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        // Read simple String values.
        final String uri = jsonObject.get(KEY_URI).getAsString();
        final String httpMethod = jsonObject.get(KEY_METHOD).getAsString();

        // Read the dynamic exchangeRates object.
        final Map<String, Float> parameters = readParametersMap(jsonObject);

        return new CurrencyExchangeResponse(uri, httpMethod, parameters);
    }

    @Nullable
    private Map<String, Float> readParametersMap(@NonNull final JsonObject jsonObject) {
        final JsonElement paramsElement = jsonObject.get(KEY_PARAMETERS);
        if (paramsElement == null) {
            // value not present at all, just return null
            return null;
        }

        final JsonObject parametersObject = paramsElement.getAsJsonObject();
        final Map<String, Float> parameters = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : parametersObject.entrySet()) {
            String key = entry.getKey();
            float value = entry.getValue().getAsFloat();
            parameters.put(key, value);
        }
        return parameters;
    }
}
