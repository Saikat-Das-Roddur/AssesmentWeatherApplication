package com.app.assesmentweatherapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.assesmentweatherapplication.Adapters.HourlyWeatherAdapter;
import com.app.assesmentweatherapplication.Config;
import com.app.assesmentweatherapplication.Model.HourlyWeather;
import com.app.assesmentweatherapplication.Model.PreviousWeather;
import com.app.assesmentweatherapplication.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class PreviousWeatherActivity extends AppCompatActivity {
    TextView textViewDate, textViewTemperature, textViewDesc,
            textViewHumidity, textViewWind,textViewSunrise, textViewSunset,
            textViewDewPoint,textViewUVI;

    ImageView imageViewWeather,imageViewWeatherSmall;
    RecyclerView recyclerViewHourly;


    PreviousWeather previousWeathers;
    HourlyWeatherAdapter hourlyWeatherAdapter;
    ArrayList<HourlyWeather> hourlyWeathers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_weather);
        previousWeathers = getIntent().getParcelableExtra("fivedays");
        textViewDate = findViewById(R.id.dateTv);
        textViewDesc = findViewById(R.id.descTv);
        textViewTemperature = findViewById(R.id.temperatureTv);
        textViewHumidity = findViewById(R.id.humidityTv);
        textViewWind = findViewById(R.id.windTv);
        textViewSunrise = findViewById(R.id.sunriseTv);
        textViewSunset = findViewById(R.id.sunsetTv);
        textViewDewPoint = findViewById(R.id.dewTv);
        textViewUVI = findViewById(R.id.uviTv);
        //imageViewWeather= findViewById(R.id.weatherIv);
        imageViewWeatherSmall = findViewById(R.id.weatherSmallIv);
        recyclerViewHourly = findViewById(R.id.hourlyRecyclerView);
        if (previousWeathers!=null){
            textViewDate.setText(previousWeathers.getDate());
            textViewDesc.setText(previousWeathers.getDescription());
            textViewDewPoint.setText(previousWeathers.getDewPoint());
            textViewTemperature.setText(previousWeathers.getTemperature());
            textViewHumidity.setText(previousWeathers.getHumidity());
            textViewSunrise.setText(previousWeathers.getSunrise());
            textViewSunset.setText(previousWeathers.getSunset());
            textViewWind.setText(previousWeathers.getWind());
            textViewUVI.setText(previousWeathers.getUvi());
            getHourlyData();
//            Picasso.get().load(Config.WEATHER_IMAGE_URL+previousWeathers.getImage()).into(imageViewWeather);
            Picasso.get().load(Config.WEATHER_IMAGE_URL+previousWeathers.getImage()).into(imageViewWeatherSmall);
           // Toast.makeText(this, previousWeathers.getDate()+"", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
        }

    }

    private void getHourlyData() {
        hourlyWeathers.clear();
        for (int i = 0; i < previousWeathers.getHourly().length(); i++) {
            try {
                String date = unixTimeConverter(previousWeathers.getHourly().getJSONObject(i).getLong("dt"));
                String temperature = new DecimalFormat("#0.0").format(previousWeathers.getHourly().getJSONObject(i).getDouble("temp") - 273.15) + "°C";
                String image = previousWeathers.getHourly().getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon") + ".png";
                String description = previousWeathers.getHourly().getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
                hourlyWeathers.add(new HourlyWeather(date, image, temperature, description));
                setHourlyAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private void setHourlyAdapter() {
        hourlyWeatherAdapter = new HourlyWeatherAdapter(hourlyWeathers, this);
        recyclerViewHourly.setAdapter(hourlyWeatherAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHourly.setLayoutManager(layoutManager);
    }
    private String unixTimeConverter(long unixTime) {
        SimpleDateFormat simpleDateFormatTime;
        Date date = new java.util.Date(unixTime * 1000L);
        simpleDateFormatTime = new SimpleDateFormat("h:mm a");
        simpleDateFormatTime.setTimeZone(TimeZone.getDefault());//java.util.TimeZone.getTimeZone("GMT-4")
        String formattedDate = simpleDateFormatTime.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(this,MainActivity.class));
//        finish();
//    }
}