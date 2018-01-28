package com.prudvi.weather.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.prudvi.weather.modle.DeserializationManager;
import com.prudvi.weather.modle.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class RequestManager {
    private static final String TAG = "RequestManager";

    //https://stackoverflow.com/questions/32416358/how-to-get-json-object-without-converting-in-retrofit-2-0

    public void requestWeatherForecast(){

        Retrofit client = WeatherForecastClient.getClient();

        WeatherForecastInterface weatherInterface = client.create(WeatherForecastInterface.class);

        Call<Object> call = weatherInterface.getWeatherForecast();

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" +
                        response.body().toString() + "]");
                new Gson().toJson(response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    public void requestWeatherForecast(String zipCode, final ForecastListener listener){

        Retrofit client = WeatherForecastClient.getClient();
        WeatherForecastInterface forecastInterface = client.create(WeatherForecastInterface.class);
        Call<JsonElement> jsonElementCall = forecastInterface.getWeatherForecast(zipCode);

        jsonElementCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                convertWeatherToPOJO(response.body(), listener);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    private void convertWeatherToPOJO(JsonElement jsonElement, ForecastListener listener){
            listener.onResult(DeserializationManager.deserializeWeather(jsonElement));
    }

    public interface ForecastListener {
        void onResult(Weather weather);
    }
}