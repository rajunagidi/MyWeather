package com.prudvi.weather.adapter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prudvi.weather.R;
import com.prudvi.weather.model.Weather;
import com.prudvi.weather.task.DownLoadUrlTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prudvi Raju on 10/6/2017.
 */

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private final String mHead;
    private final List<Weather> mWeathers;
    private UNITS mDisplayUnits = UNITS.C;

    public WeatherRecyclerViewAdapter(List<Weather> weathers) {
        mHead = "Today";
        mWeathers = new ArrayList<>();
        mWeathers.addAll(weathers);
    }

    public UNITS getDisplayUnits() {
        return mDisplayUnits;
    }

    public void setDisplayUnits(UNITS displayUnits) {
        this.mDisplayUnits = displayUnits;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_head_day, parent, false);
            return new ViewHolderHeadDay(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_hour, parent, false);
            return new ViewHolderItemHour(view);
        }

        throw new RuntimeException(" IllegalArgument  .... ");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderHeadDay) {
            ((ViewHolderHeadDay) holder).mDay.setText(mHead);
        } else if (holder instanceof ViewHolderItemHour) {
            final ViewHolderItemHour hourHolder = (ViewHolderItemHour) holder;
//            Weather weather =
            final Weather weather = mWeathers.get(position - 1);
            hourHolder.mTime.setText(weather.getTime());
            hourHolder.mTemperature.setText( mDisplayUnits == UNITS.C ? weather.getTempC():weather.getTempF());
            new DownLoadUrlTask(weather.getIconURL(), new DownLoadUrlTask.OnDownLoadUrlListener() {
                @Override
                public void onDownload(Bitmap icons) {
                    hourHolder.mWeather.setImageBitmap(icons);
                }

                @Override
                public void onFail() {

                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            int color = hourHolder.mTime.getContext().getResources().getColor(R.color.colorText);
            if (weather.isHottest()) {
                color = hourHolder.mTime.getContext().getResources().getColor(R.color.colorHot);
            } else if (weather.isCoolest()) {
                color = hourHolder.mTime.getContext().getResources().getColor(R.color.colorCool);
            }
            hourHolder.mTime.setTextColor(color);
            hourHolder.mTemperature.setTextColor(color);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEAD : VIEW_TYPE_ITEM;
    }

    private int getWeatherImage(Weather weather) {
        return R.drawable.weather_sunny;
    }

    @Override
    public int getItemCount() {
        return mWeathers.size() + 1;
    }

    public enum UNITS {C, F}

    public class ViewHolderHeadDay extends RecyclerView.ViewHolder {

        private final TextView mDay;

        public ViewHolderHeadDay(View itemView) {
            super(itemView);
            mDay = itemView.findViewById(R.id.day);
        }
    }

    public class ViewHolderItemHour extends RecyclerView.ViewHolder {

        private final TextView mTime;
        private final ImageView mWeather;
        private final TextView mTemperature;

        ViewHolderItemHour(View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.hour);
            mWeather = itemView.findViewById(R.id.weather);
            mTemperature = itemView.findViewById(R.id.temperature);
        }
    }
}
