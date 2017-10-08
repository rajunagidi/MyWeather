package com.prudvi.weather.listener;

import com.prudvi.weather.jsonparser.WeatherForecast;

/**
 * Created by Prudvi Raju on 10/7/2017.
 */

public interface OnForecastListener {
    void onSuccess(WeatherForecast forecast);
    void onError();

}
