package com.prudvi.weather.modle;

/**
 * Created by ESHAN on 1/26/2018.
 */

public class CurrentObservation {

    public String temp_c;
    public String temp_f;
    public String icon;
    public String icon_url;
    public String display_name;

    public String getTempC() {
        return temp_c;
    }

    public String getTempF() {
        return temp_f;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconUrl() {
        return icon_url;
    }

    public String getLocation() {
        display_name = display_name.replace("\"", "");
        return display_name;
    }
}
