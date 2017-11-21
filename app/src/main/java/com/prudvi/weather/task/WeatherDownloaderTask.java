package com.prudvi.weather.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Downloads weather from Underground
 */

public class WeatherDownloaderTask extends AsyncTask<Void, Void, String> {

    private final OnWeatherDownloadListener mListener;
    private final String request;
    private static final String TAG = "WeatherDownloaderTask";

    public WeatherDownloaderTask(String zipCode, OnWeatherDownloadListener listener) {
        mListener = listener;
        request = String.format("http://api.wunderground.com/api/eae26379352995a4/conditions/hourly/q/%s.json", zipCode);
    }

    @Override
    protected String doInBackground(Void... voids) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(request);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-encoding", "identity");
            connection.connect();
            int resCode = connection.getResponseCode();
            if (resCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
//            int length = connection.getContentLength();
            StringBuilder buffer = new StringBuilder();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                buffer.append(readLine);
            }

            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "malformed URL",e);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (mListener == null) return;

        if (result != null) {
            mListener.onSuccess(result);
        } else {
            mListener.onError();
        }
    }

    public interface OnWeatherDownloadListener {
        void onSuccess(String json);

        void onError();
    }
}
