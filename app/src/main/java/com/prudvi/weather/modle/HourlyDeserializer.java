package com.prudvi.weather.modle;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class HourlyDeserializer implements JsonDeserializer<WeatherHourly> {
    private static final String TAG = "HourlyDeserializer";

    @Override
    public WeatherHourly deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {

        Gson gson = new Gson();
        WeatherHourly hourly = gson.fromJson(json, WeatherHourly.class);
        JsonElement temp = json.getAsJsonObject().get("temp");
        if (!temp.isJsonNull()) {
            hourly.metric = temp.getAsJsonObject().get("metric").getAsString();
            hourly.english = temp.getAsJsonObject().get("english").getAsString();
        }

        JsonElement fcttime = json.getAsJsonObject().get("FCTTIME");
        if (!fcttime.isJsonNull()) {
            hourly.civil = fcttime.getAsJsonObject().get("civil").toString();
        }
        return hourly;
    }
}
