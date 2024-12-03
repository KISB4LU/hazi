package window.login.adapters;

import com.google.gson.*;
import indicators.*;

import java.lang.reflect.Type;

public class IndicatorAdapter implements JsonDeserializer<indicator>, JsonSerializer<indicator> {
    @Override
    public JsonElement serialize(indicator src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", src.getClass().getSimpleName()); // Típus tárolása
        jsonObject.add("properties", context.serialize(src));
        return jsonObject;
    }

    @Override
    public indicator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement properties = jsonObject.get("properties");

        switch (type) {
            case "MovingAvrage":
                return context.deserialize(properties, MovingAvrage.class);
            case "ExpMovingAvrage":
                return context.deserialize(properties, ExpMovingAvrage.class);
            case "BollingerBands":
                return context.deserialize(properties, BollingerBands.class);
            case "ParabolicSAR":
                return context.deserialize(properties, ParabolicSAR.class);
            case "Ichimoku":
                return context.deserialize(properties, Ichimoku.class);
            default:
                throw new JsonParseException("Ismeretlen típus: " + type);
        }
    }
}

