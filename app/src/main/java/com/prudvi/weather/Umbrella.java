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

import com.prudvi.weather.adapter.WeatherRecyclerViewAdapter;
import com.prudvi.weather.jsonparser.WeatherForecast;
import com.prudvi.weather.jsonparser.WeatherForecastManager;
import com.prudvi.weather.listener.OnForecastListener;
import com.prudvi.weather.model.Weather;
import com.prudvi.weather.util.AppUtils;

public class Umbrella extends AppCompatActivity {

    private static String TAG = Umbrella.class.getSimpleName();

    TextView mLocation;
    TextView mWeather;
    TextView mTemperature;
    ImageView mSettings;
    RecyclerView detailedView;
    RelativeLayout mTopSession;
    private WeatherForecastManager mForeCastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella);
        mForeCastManager = new WeatherForecastManager(Umbrella.this);

        mLocation = (TextView) findViewById(R.id.current_location);
        mTemperature = (TextView) findViewById(R.id.current_temperature);
        mWeather = (TextView) findViewById(R.id.current_weather);
        mTopSession = (RelativeLayout) findViewById(R.id.top_session);
        detailedView = (RecyclerView) findViewById(R.id.weather_today);

        GridLayoutManager layoutManager = new GridLayoutManager(Umbrella.this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 3 : 1;
            }
        });
        detailedView.setLayoutManager(layoutManager);

        ImageView settings = (ImageView) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Umbrella.this, SettingsActivity.class));
            }
        });

    }

    public void onResume() {
        super.onResume();
        String zip = AppUtils.getZipCode(Umbrella.this);
        if (zip == null) {
            Toast.makeText(Umbrella.this, "Zip code requied for forecst", Toast.LENGTH_SHORT).show();
            return;
        }

        mForeCastManager.getForeCastInfo(zip, new OnForecastListener() {
            @Override
            public void onSuccess(WeatherForecast forecast) {
                updateUI(forecast);
            }

            @Override
            public void onError() {

            }
        });
    }

    private void updateUI(WeatherForecast forecast) {
        if (isDestroyed()) return;
        if (forecast.getObservation() != null) {
            mLocation.setText(forecast.getObservation().getLocation());
            mTemperature.setText(forecast.getObservation().getTempC());
            mWeather.setText(forecast.getObservation().getWeatherCondition());
            mTopSession.setBackgroundColor(AppUtils.getSessionColor(forecast.getObservation().getTempF()));
        }

        if (forecast.getWeathers() != null) {
            Weather weatherHot = AppUtils.getMaxTemperature(forecast.getWeathers());
            weatherHot.setHottest();
            Weather weatherCool = AppUtils.getMinTemperature(forecast.getWeathers());
            weatherCool.setLowest();


            WeatherRecyclerViewAdapter adapter = new WeatherRecyclerViewAdapter("Today", forecast.getWeathers());
            detailedView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
