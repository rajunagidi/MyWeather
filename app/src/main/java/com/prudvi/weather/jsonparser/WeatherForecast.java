package com.prudvi.weather.jsonparser;

import com.prudvi.weather.model.CurrentObservation;
import com.prudvi.weather.model.Weather;

import java.util.List;

/**
 * Created by Prudvi Raju on 10/7/2017.
 */

public class WeatherForecast {

    private final CurrentObservation mObservation;
    private final List<Weather> mWeathers;

    public WeatherForecast(CurrentObservation observation, List<Weather> weathers) {
        mObservation = observation;
        mWeathers = weathers;
    }

    public CurrentObservation getObservation() {
        return mObservation;
    }

    public List<Weather> getWeathers() {
        return mWeathers;
    }


}
