package com.prudvi.weather.modle;

import java.util.List;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class Weather {
    CurrentObservation mCurrentObservation;
    List<WeatherHourly> mWeatherHourly;

    public CurrentObservation getObservation() {
        return mCurrentObservation;
    }

    public List<WeatherHourly> getWeatherHourly() {
        return mWeatherHourly;
    }
}
