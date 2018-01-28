package com.prudvi.weather.modle;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class WeatherDeserializer implements JsonDeserializer<Weather> {
    private static final String TAG = "WeatherDeserializer";

    @Override
    public Weather deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {
        Log.d(TAG, "deserialize() called with: json = [" + json + "], typeOfT = [" + typeOfT +
                "], context = [" + context + "]");

        Weather weather = new Weather();
        weather.mCurrentObservation = deserializeCurrentObservation(json);
        weather.mWeatherHourly = deserializeHourly(json);
        return weather;
    }

    private CurrentObservation deserializeCurrentObservation(JsonElement element) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(CurrentObservation.class, new CurrentObservationDeserializer());
        Gson gson = builder.create();
        CurrentObservation observation = gson.fromJson(element.getAsJsonObject().get
                ("current_observation"), CurrentObservation.class);
        return observation;
    }

    public List<WeatherHourly> deserializeHourly(JsonElement element) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(WeatherHourly.class, new HourlyDeserializer());
        Gson gson = builder.create();

        JsonArray hourly = element.getAsJsonObject().getAsJsonArray("hourly_forecast");
        List<WeatherHourly> weatherHourlies = new ArrayList<>();

        if (hourly != null) {
            for (JsonElement item : hourly) {
                weatherHourlies.add(gson.fromJson(item, WeatherHourly.class));
            }
        }
        return weatherHourlies;
    }
}
