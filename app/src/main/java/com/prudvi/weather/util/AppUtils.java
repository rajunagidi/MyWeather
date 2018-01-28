package com.prudvi.weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.prudvi.weather.R;
import com.prudvi.weather.SettingsActivity;
import com.prudvi.weather.modle.WeatherHourly;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Provides App helper functions
 */
public class AppUtils {

    private static final Comparator COMPARATOR = new Comparator<WeatherHourly>() {
        @Override
        public int compare(WeatherHourly o1, WeatherHourly o2) {
            if (Float.parseFloat(o1.getTempF()) > Float.parseFloat(o2.getTempF())) return 1;
            return (Float.parseFloat(o1.getTempF()) < Float.parseFloat(o2.getTempF())) ? -1 : 0;
        }
    };

    public static WeatherHourly getMinTemperature(List<WeatherHourly> weathers) {
        return Collections.min(weathers, COMPARATOR);
    }

    public static WeatherHourly getMaxTemperature(List<WeatherHourly> weathers) {
        return Collections.max(weathers, COMPARATOR);
    }

    public static String getZipCode(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SettingsActivity.SettingsFragment.ZIP_CODE, "14580");
    }

    public static String getUnites(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SettingsActivity.SettingsFragment.UNITS, null);
    }

    public static int getSessionColor(String tempF) {
        return Float.parseFloat(tempF) < 60 ? R.color.colorCool : R.color.colorHot;
    }

    @SuppressWarnings("unused")
    public static void writeToExternalStorage(String data) throws IOException {
        String tmpFile = "weather_son.txt";
        File externalFolder = Environment.getExternalStorageDirectory();
        File jsonOutputFile = new File(externalFolder, tmpFile);
        if (jsonOutputFile.exists()) {
            jsonOutputFile.delete();
        }
        jsonOutputFile.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonOutputFile))) {
            writer.write(data);
        }
    }

    @SuppressWarnings("unused")
    public static String readFromExternalStorage() throws IOException {
        String tmpFile = "weather_son.txt";
        File externalFolder = Environment.getExternalStorageDirectory();
        File jsonOutputFile = new File(externalFolder, tmpFile);

        if (jsonOutputFile.exists()) {
            StringBuilder buffer = new StringBuilder();
            String data;
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonOutputFile))) {
                while ((data = reader.readLine()) != null) {
                    buffer.append(data);
                }
                return buffer.toString();
            }
        }
        return null;
    }

}

