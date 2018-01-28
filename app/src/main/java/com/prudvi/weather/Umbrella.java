package com.prudvi.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prudvi.weather.adapter.WeatherAdapter;
import com.prudvi.weather.modle.Weather;
import com.prudvi.weather.modle.WeatherHourly;
import com.prudvi.weather.retrofit.RequestManager;
import com.prudvi.weather.util.AppUtils;

import butterknife.BindView;

public class Umbrella extends AppCompatActivity {

    private static final String TAG = "Umbrella";
    private @BindView(R.id.current_location)
    TextView mLocation;
    private @BindView(R.id.current_weather)
    TextView mWeather;
    private @BindView(R.id.current_temperature)
    TextView mTemperature;
    private @BindView(R.id.top_session)
    RelativeLayout mTopSession;
    private @BindView(R.id.weather_today)
    RecyclerView detailedView;
    private @BindView(R.id.settings)
    ImageView mSettings;
    private int mMeasuringUnits = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella);
        //Todo for next version collapsible tool bar
        //Toolbar bar = findViewById(R.id.toolbar);
        //setSupportActionBar(bar);

        GridLayoutManager layoutManager = new GridLayoutManager(Umbrella.this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 3 : 1;
            }
        });
        detailedView.setLayoutManager(layoutManager);

        mSettings.setOnClickListener((View v) -> {
            startActivity(new Intent(Umbrella.this, SettingsActivity.class));
        });

    }

    public void onResume() {
        super.onResume();
        String zip = AppUtils.getZipCode(Umbrella.this);
        String unites = AppUtils.getUnites(Umbrella.this);
        mMeasuringUnits = "0".equals(unites) ? 0 : 1;
        if (zip == null) {
            Toast.makeText(Umbrella.this, "Zip code required for forecast", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        new RequestManager().requestWeatherForecast(zip, (Weather weather)-> {
                updateUI(weather);
        });
    }

    private void updateUI(Weather forecast) {
        if (isDestroyed()) return;
        if (forecast.getObservation() != null) {
            mLocation.setText(forecast.getObservation().getLocation());
            mTemperature.setText(mMeasuringUnits == 0 ? forecast.getObservation().getTempC() :
                    forecast.getObservation().getTempF());
            mWeather.setText(forecast.getObservation().display_name);
            mTopSession.setBackgroundColor(AppUtils.getSessionColor(forecast.getObservation()
                    .getTempF()));
        }

        if (forecast.getWeatherHourly() != null && forecast.getWeatherHourly().size() > 1) {
            WeatherHourly weatherHot = AppUtils.getMaxTemperature(forecast.getWeatherHourly());
            weatherHot.setHottest(true);
            WeatherHourly weatherCool = AppUtils.getMinTemperature(forecast.getWeatherHourly());
            weatherCool.setCoolest(true);


            WeatherAdapter adapter = new WeatherAdapter(this, forecast
                    .getWeatherHourly());
            adapter.setDisplayUnits(mMeasuringUnits == 0 ? WeatherAdapter.UNITS.C :
                    WeatherAdapter.UNITS.F);
            detailedView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Invalid zip code ", Toast.LENGTH_SHORT).show();
        }
    }
}
