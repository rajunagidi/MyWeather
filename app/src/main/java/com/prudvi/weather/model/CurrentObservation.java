package com.prudvi.weather.model;

/**
 * Created by Prudvi Raju on 10/7/2017.
 */

public class CurrentObservation {
    private final String mLocation;
    private final String mWeatherCondition;
    private final String mTempF;
    private final String mTempC;
    private String mUrl;

    public CurrentObservation(String tempC, String tempF, String location, String weater) {
        mTempC = tempC;
        mTempF = tempF;
        mLocation = location;
        mWeatherCondition = weater;
    }

    public String getWeatherCondition() {
        return mWeatherCondition;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getTempF() {
        return mTempF;
    }

    public String getTempC() {
        return mTempC;
    }
}
