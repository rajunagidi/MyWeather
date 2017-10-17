package com.prudvi.weather.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prudvi Raju on 10/16/2017.
 */

public class DownLoadUrlTask extends AsyncTask<Void, Void, Bitmap> {

    private final String mUrl;
    private final OnDownLoadUrlListener mListener;
    private LruCache<String, Bitmap> mBitmaps = new LruCache(10);

    public DownLoadUrlTask(String urlToDownload, OnDownLoadUrlListener listener) {
        mUrl = urlToDownload;
        mListener = listener;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = mBitmaps.get(mUrl);
        if (bitmap != null) {
            return bitmap;
        }
        HttpURLConnection connection = null;
        try {
            URL url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
            int length = connection.getContentLength();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK && length < 1) return null;

            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            mBitmaps.put(mUrl, bitmap);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }
        return bitmap;
    }

    @Override
    public void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (mListener == null) return;

        if (bitmap != null) {
            mListener.onDownload(bitmap);
        } else {
            mListener.onFail("Failed to download");
        }
    }

    public interface OnDownLoadUrlListener {
        void onDownload(Bitmap icons);
        void onFail(String error);
    }

}
