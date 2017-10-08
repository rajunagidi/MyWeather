package com.prudvi.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prudvi.weather.R;
import com.prudvi.weather.model.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prudvi Raju on 10/6/2017.
 */

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEAD = 0;
    public static final int VIEW_TYPE_ITEM = 1;
    private final String mHead;
    private final List<Weather> mWeathers;

    public WeatherRecyclerViewAdapter(String header, List<Weather> weathers) {
        mHead = header;
        mWeathers = new ArrayList<>();
        mWeathers.addAll(weathers);
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
            ViewHolderItemHour hourHolder = (ViewHolderItemHour) holder;
//            Weather weather =
            final Weather weather = mWeathers.get(position - 1);
            hourHolder.mTime.setText(weather.getTime());
            hourHolder.mTemperature.setText(weather.getTempC());
            hourHolder.mWeather.setImageResource(getWeatherImage(weather));

            int color = hourHolder.mTime.getContext().getResources().getColor(R.color.colorText);
            if(weather.isHottest()){
                color = hourHolder.mTime.getContext().getResources().getColor(R.color.colorHot);
            }else if(weather.isCoolest()){
                color = hourHolder.mTime.getContext().getResources().getColor(R.color.colorCool);
            }
            hourHolder.mTime.setTextColor(color);
            hourHolder.mTemperature.setTextColor(color);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEAD: VIEW_TYPE_ITEM;
    }

    private int getWeatherImage(Weather weather) {
        return R.drawable.weather_sunny;
    }

    @Override
    public int getItemCount() {
        return mWeathers.size() + 1;
    }

    public class ViewHolderHeadDay extends RecyclerView.ViewHolder {

        public TextView mDay;

        public ViewHolderHeadDay(View itemView) {
            super(itemView);
            mDay = (TextView) itemView.findViewById(R.id.day);
        }
    }

    public class ViewHolderItemHour extends RecyclerView.ViewHolder {

        public TextView mTime;
        public ImageView mWeather;
        public TextView mTemperature;

        public ViewHolderItemHour(View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.hour);
            mWeather = (ImageView) itemView.findViewById(R.id.weather);
            mTemperature = (TextView) itemView.findViewById(R.id.temperature);
        }
    }
}
