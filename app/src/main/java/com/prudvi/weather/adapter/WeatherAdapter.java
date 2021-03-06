package com.prudvi.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prudvi.weather.modle.Weather;
import com.prudvi.weather.modle.WeatherHourly;
import com.prudvi.weather.util.PicassoLoadIcon;
import com.prudvi.weather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prudvi Raju on 10/6/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private final String mHead;
    private final List<WeatherHourly> mWeathers;
    private final Context mContext;
    private UNITS mDisplayUnits = UNITS.C;

    public WeatherAdapter(Context context, List<WeatherHourly> weathers) {
        mContext = context;
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .recycler_view_head_day, parent, false);
            return new ViewHolderHeadDay(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .recycler_view_item_hour, parent, false);
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
            final WeatherHourly weather = mWeathers.get(position - 1);
            hourHolder.mTime.setText(weather.getTime());
            hourHolder.mTemperature.setText(mDisplayUnits == UNITS.C ? weather.getTempC() :
                    weather.getTempF());
            //replace async wiht picasso
            PicassoLoadIcon.loadImage(mContext, hourHolder.mWeather, weather.getIconURL());
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
