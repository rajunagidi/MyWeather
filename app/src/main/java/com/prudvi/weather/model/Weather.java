package com.prudvi.weather.model;

/**
 * Created by Prudvi Raju on 10/6/2017.
 */

public class Weather {

    private final String mTempC;
    private final String mTempF;
    private final String mTime;
    Units mUnit = Units.FAHREN;
    private String mWeather;
    private String mIconURL;
    private boolean mHottest;
    private boolean mCoolest;
    private int mUnits;


    public Weather(String tempC, String tempF, String time) {
        mTempC = tempC;
        mTempF = tempF;
        mTime = time;
    }

    public String getWeather() {
        return mWeather;
    }

    public void setWeather(String mWeather) {
        this.mWeather = mWeather;
    }

    public String getIconURL() {
        return mIconURL;
    }

    public void setIconURL(String mIconURL) {
        this.mIconURL = mIconURL;
    }

    public String getTempC() {
        return mTempC;
    }

    public String getTempF() {
        return mTempF;
    }

    public String getTime() {
        return mTime;
    }

    public int getUnits() {
        return mUnits;
    }

    public void setUnits(int mUnits) {
        this.mUnits = mUnits;
    }

    public boolean isHottest() {
        return mHottest;
    }

    public void setHottest() {
        this.mHottest = true;
    }

    public boolean isCoolest() {
        return mCoolest;
    }

    public void setLowest() {
        this.mCoolest = true;
    }

    enum Units {FAHREN, CENT}
}
