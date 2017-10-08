package com.prudvi.weather.task;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Prudvi Raju on 10/7/2017.
 */

public class WeatherDownloaderTask extends AsyncTask<Void, Void, String> {

    OnWeatherDownloadListener mListener;
    String mZipCode;
    String request;

    public WeatherDownloaderTask(String zipCode, OnWeatherDownloadListener listener) {
        mListener = listener;
        mZipCode = zipCode;
        request = String.format("http://api.wunderground.com/api/eae26379352995a4/conditions/hourly/q/%s.json", mZipCode);
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
            int lenght = connection.getContentLength();
            StringBuffer buffer = new StringBuffer();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                buffer.append(readLine);
            }

            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
            mListener.onError(0);
        }
    }

    public interface OnWeatherDownloadListener {
        void onSuccess(String json);

        void onError(int errorCode);
    }
}
