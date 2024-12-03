package window.login.adapters;

import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonDeserializer<Color>, JsonSerializer<Color> {
    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            try {
                return Color.decode(json.getAsString());
            } catch (NumberFormatException e) {
                return Color.YELLOW; // Alapértelmezett szín, ha nem érvényes a színkód
            }
        }
        return Color.YELLOW; // Alapértelmezett szín
    }

    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(String.format("#%02X%02X%02X", src.getRed(), src.getGreen(), src.getBlue()));
    }
}
