package com.prudvi.weather.modle;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class CurrentObservationDeserializer implements JsonDeserializer<CurrentObservation> {
    private static final String TAG = "CurrentObservationDeser";

    @Override
    public CurrentObservation deserialize(JsonElement json, Type typeOfT,
                                          JsonDeserializationContext context) throws
            JsonParseException {
        CurrentObservation currentObservation = new Gson().fromJson(json, CurrentObservation.class);
        JsonObject name = json.getAsJsonObject().get("display_location").getAsJsonObject();
        String location = name.get("full").toString();
        currentObservation.display_name = location;
        return currentObservation;
    }
}
