package com.prudvi.weather.modle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class DeserializationManager {

    private static final String TAG = "DeserializationManager";
    public static Weather deserializeWeather(JsonElement element){

        GsonBuilder builder = new GsonBuilder();
        WeatherDeserializer deserializer = new WeatherDeserializer();
        builder.registerTypeAdapter(Weather.class, deserializer);
        Gson customGson = builder.create();
        Weather weather = customGson.fromJson(element, Weather.class);
//        GsonBuilder
        return weather;
    }

    public static WeatherHourly deserializeHourly(JsonElement element){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(WeatherHourly.class, new HourlyDeserializer());
        Gson gson = builder.create();

        WeatherHourly hourly = gson.fromJson(element.getAsJsonObject().get("hourly_display"), WeatherHourly.class);

        return new WeatherHourly();
    }

    public static CurrentObservation deserializeCurrentObservation(JsonElement element){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(CurrentObservation.class, new CurrentObservationDeserializer());
        Gson gson = builder.create();
        CurrentObservation observation = gson.fromJson(element.getAsJsonObject().get("current_observation"), CurrentObservation.class);
        return new CurrentObservation();
    }
}
