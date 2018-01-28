package com.prudvi.weather.retrofit;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ESHAN on 1/26/2018.
 */

public interface WeatherForecastInterface {

    @GET("/api/eae26379352995a4/conditions/hourly/q/14580.json")
    Call<Object> getWeatherForecast();

    @GET("/api/eae26379352995a4/conditions/hourly/q/{zip_code}.json")
    Call<JsonElement> getWeatherForecast(@Path("zip_code") String zip);

}
