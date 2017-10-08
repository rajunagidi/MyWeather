package com.prudvi.weather.jsonparser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.prudvi.weather.listener.OnForecastListener;
import com.prudvi.weather.model.CurrentObservation;
import com.prudvi.weather.model.Weather;
import com.prudvi.weather.task.WeatherDownloaderTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prudvi Raju on 10/7/2017.
 */

public class WeatherForecastManager {

    private static final String TAG = WeatherForecastManager.class.getSimpleName();
    private final Context mContext;

    public WeatherForecastManager(Context context) {
        mContext = context;
        if (mContext == null) throw new RuntimeException("Invalid Argument exception");
    }

    public void getForeCastInfo(String zip, final OnForecastListener listener) {
        if (zip == null) return;
        WeatherDownloaderTask task = new WeatherDownloaderTask(zip, new WeatherDownloaderTask.OnWeatherDownloadListener() {
            @Override
            public void onSuccess(String json) {
                listener.onSuccess(parseWeatherJSON(json));
            }

            @Override
            public void onError(int errorCode) {
                listener.onError();
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public WeatherForecast parseWeatherJSON(String json) {

        CurrentObservation currentObservation = null;
        List<Weather> mHourlyWeather = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            if (!jsonObject.isNull("current_observation")) {
                JSONObject observation = jsonObject.getJSONObject("current_observation");
                currentObservation = parseObservation(observation);
            }
            if (!jsonObject.isNull("hourly_forecast")) {
                JSONArray observation = jsonObject.optJSONArray("hourly_forecast");
                mHourlyWeather = parseHourly(observation);
            }

        } catch (JSONException e) {
            Log.d(TAG, "JSON Exception", e);
        }

        return new WeatherForecast(currentObservation, mHourlyWeather);
    }

    private CurrentObservation parseObservation(JSONObject object) throws JSONException {
        if (object == null) return null;
        String temp_c = null;
        String temp_f = null;
        String weather = null;
        String icon_url = null;
        String displayLocationName = null;

        if (!object.isNull("temp_c")) {
            temp_c = object.getString("temp_c");
        }
        if (!object.isNull("temp_f")) {
            temp_f = object.getString("temp_f");
        }
        if (!object.isNull("icon")) {
            weather = object.getString("icon");
        }
        if (!object.isNull("icon_url")) {
            icon_url = object.getString("icon_url");
        }
        JSONObject displayLocation = object.getJSONObject("display_location");
        if (displayLocation != null && (!displayLocation.isNull("full"))) {
            displayLocationName = displayLocation.getString("full");
        }

        Log.d(TAG, String.format("Observation: display Name: %s, weather: %s, temp_c: %s, temp_f: %s, icon_url: %s", displayLocationName, weather, temp_c, temp_f, icon_url));

        return new CurrentObservation(temp_c, temp_f, displayLocationName, weather);
    }

    private List<Weather> parseHourly(JSONArray hourArray) throws JSONException {

        List<Weather> mWeatherHourly = new ArrayList<>();
        for (int i = 0; i < hourArray.length(); i++) {
            JSONObject jsonObject = hourArray.getJSONObject(i);
            String hour = null;
            String temp_c = null;
            String temp_f = null;
            String weather = null;
            String icon_url = null;

            if (!jsonObject.isNull("wx")) {
                weather = jsonObject.getString("wx");
            }

            if (!jsonObject.isNull("icon_url")) {
                icon_url = jsonObject.getString("icon_url");
            }

            if (!jsonObject.isNull("temp")) {
                JSONObject tempObject = jsonObject.getJSONObject("temp");
                temp_c = tempObject.getString("metric");
                temp_f = tempObject.getString("english");
            }

            if (!jsonObject.isNull("FCTTIME")) {
                JSONObject hourObject = jsonObject.getJSONObject("FCTTIME");
                hour = hourObject.getString("civil");
            }

            Weather climate = new Weather(temp_c, temp_f, hour);
            climate.setWeather(weather);
            climate.setIconURL(icon_url);
            mWeatherHourly.add(climate);
            Log.d(TAG, String.format("Hourly Display : Hour: %s, Weather: %s, temp_c: %s, temp_f: %s, icon_url: %s", hour, weather, temp_c, temp_f, icon_url));
        }
        return mWeatherHourly;
    }

}
