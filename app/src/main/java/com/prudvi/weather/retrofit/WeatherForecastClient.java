package com.prudvi.weather.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class WeatherForecastClient {
    public static String BASE_URL = "http://api.wunderground.com";

    public static Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory
                (GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}

