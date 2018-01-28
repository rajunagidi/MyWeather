package com.prudvi.weather.modle;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class WeatherHourly {

    public String wx;
    public String icon_url;
    public String metric;
    public String english;
    public String civil;
    private boolean mHottest;
    private boolean mCoolest;

    public void setHottest(boolean hottest) {
        this.mHottest = hottest;
    }

    public void setCoolest(boolean coolest) {
        this.mCoolest = coolest;
    }

    public String getWeather() {
        wx = wx.replace("\"", "");
        return wx;
    }

    public String getIconURL() {
        return icon_url;
    }

    public String getTempC() {
        metric = metric.replace("\"", "");
        return metric;
    }

    public String getTempF() {
        english = english.replace("\"", "");
        return english;
    }

    public String getTime() {
        civil = civil.replace("\"", "");
        return civil;
    }

    public boolean isHottest() {
        return mHottest;
    }

    public boolean isCoolest() {
        return mCoolest;
    }
}
