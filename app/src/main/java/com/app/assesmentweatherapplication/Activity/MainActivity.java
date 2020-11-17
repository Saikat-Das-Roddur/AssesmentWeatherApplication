package com.app.assesmentweatherapplication.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.assesmentweatherapplication.Adapters.HourlyWeatherAdapter;
import com.app.assesmentweatherapplication.Adapters.SevenDaysWeatherAdapter;
import com.app.assesmentweatherapplication.Config;
import com.app.assesmentweatherapplication.Model.SevenDaysWeather;
import com.app.assesmentweatherapplication.Model.HourlyWeather;
import com.app.assesmentweatherapplication.Model.PreviousWeather;
import com.app.assesmentweatherapplication.R;
import com.app.assesmentweatherapplication.Server.ServerCalling;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    TextView textViewCity, textViewToday, textViewDate,
            textViewTemperature, textViewTime, textViewFeels,
            textViewCountry,textViewDescription,textViewSunset,
            textViewSunrise,textViewHumidity,textViewUvi, textViewWindSpeed;
    ImageView imageViewWeather;
    RecyclerView recyclerViewHourly,recyclerViewSevenDays, recyclerViewPrevious;
    HourlyWeatherAdapter hourlyWeatherAdapter;
    SevenDaysWeatherAdapter sevenDaysWeatherAdapter;
    RecyclerView.LayoutManager layoutManagerHourly, layoutManagerSevenDays;
    boolean currentCity = false;
    ArrayList<HourlyWeather> hourlyWeathers = new ArrayList<>();
    ArrayList<SevenDaysWeather> sevenDaysWeathers = new ArrayList<>();
    ArrayList<PreviousWeather> previousWeathers = new ArrayList<>();
    //LocationManager locationManager;
    LocationListener locationListener;
    String TAG = getClass().getSimpleName(), latitude="", longitude="";
    long timezone=0,unixDate=0;
    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(this);
        recyclerViewHourly = findViewById(R.id.hourlyRecyclerView);
        recyclerViewSevenDays = findViewById(R.id.sevenDaysRecyclerView);
        textViewCity = findViewById(R.id.cityTv);
        textViewCountry = findViewById(R.id.countryTv);
        textViewDescription = findViewById(R.id.descTv);
        textViewToday = findViewById(R.id.todayTv);
        textViewDate = findViewById(R.id.dateTv);
        textViewTemperature = findViewById(R.id.temperatureTv);
        textViewTime = findViewById(R.id.timeTv);
        textViewFeels = findViewById(R.id.feelsTv);
        imageViewWeather = findViewById(R.id.weatherIv);
        textViewSunset = findViewById(R.id.sunsetTv);
        textViewSunrise = findViewById(R.id.sunriseTv);
        textViewUvi = findViewById(R.id.uviTv);
        textViewHumidity = findViewById(R.id.humidityTv);
        textViewWindSpeed = findViewById(R.id.windTv);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        textViewTime.setText(new SimpleDateFormat("h:mm a").format(new Date()));
//        textViewDate.setText(new SimpleDateFormat("EEE, d MMM").format(new Date()));
       // statusCheck();
           getCurrentLocation();




    }

    private void getCurrentLocation() {
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }else {
            //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());
                    getCurrentLocationWeather(latitude, longitude);
                    getHourlySevenDaysWeather(latitude, longitude);
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                return;
            }
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000, 1000
                    , locationListener);
        }
    }

    private void getHourlySevenDaysWeather(String latitude, String longitude) {
        ServerCalling.getHourlySevenDaysWeather(latitude, longitude, new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {



                try {
                    textViewSunrise.setText(unixTimeConverter(response.getJSONObject("current").getLong("sunrise")));
                    textViewSunset.setText(unixTimeConverter(response.getJSONObject("current").getLong("sunset")));
                    textViewHumidity.setText(response.getJSONObject("current").getString("humidity")+"%");
                    textViewUvi.setText(response.getJSONObject("current").getString("uvi"));
                    textViewWindSpeed.setText(response.getJSONObject("current").getString("wind_speed"));

                    JSONArray jsonArrayHourly = response.getJSONArray("hourly");
                    JSONArray jsonArrayDaily = response.getJSONArray("daily");
                    hourlyWeathers.clear();
                    for (int i = 0; i < jsonArrayHourly.length(); i++) {
                        String date = unixTimeConverter(jsonArrayHourly.getJSONObject(i).getLong("dt"));
                        String temperature = new DecimalFormat("#0.00").format(jsonArrayHourly.getJSONObject(i).getDouble("temp")-273.15)+"°C";
                        String image = jsonArrayHourly.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon")+".png";
                        String description = jsonArrayHourly.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");

                        hourlyWeathers.add(new HourlyWeather(date,image,temperature,description));
                    }
                    sevenDaysWeathers.clear();
                    for (int i = 0; i < jsonArrayDaily.length(); i++) {
                        String date = unixDateConverter(jsonArrayDaily.getJSONObject(i).getLong("dt"));
                        String minTemperature = new DecimalFormat("#0.00").format(jsonArrayDaily.getJSONObject(i).getJSONObject("temp").getDouble("min")-273.15);
                        String maxTemperature = new DecimalFormat("#0.00").format(jsonArrayDaily.getJSONObject(i).getJSONObject("temp").getDouble("max")-273.15);
                        String image = jsonArrayHourly.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon")+".png";
                        String description = jsonArrayHourly.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");

                        sevenDaysWeathers.add(new SevenDaysWeather(date,minTemperature,maxTemperature,image));
                    }
                    setHourlyAdapter();
                    setSevenDaysAdapter();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(MainActivity.this, anError.getErrorCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSevenDaysAdapter() {
        sevenDaysWeatherAdapter = new SevenDaysWeatherAdapter(sevenDaysWeathers,this);
        recyclerViewSevenDays.setAdapter(sevenDaysWeatherAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSevenDays.setLayoutManager(layoutManager);
    }

    private void setHourlyAdapter() {
        hourlyWeatherAdapter = new HourlyWeatherAdapter(hourlyWeathers,this);
        recyclerViewHourly.setAdapter(hourlyWeatherAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewHourly.setLayoutManager(layoutManager);
    }

    private void getCurrentLocationWeather(String latitude, String longitude) {
        ServerCalling.getCurrentLocationWeather(latitude, longitude, new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "onResponse: "+response);
                    //JSONArray jsonArray = response.getJSONArray("weather");
                    textViewCity.setText(response.getString("name")+", "+response.getJSONObject("sys").getString("country"));
                    //textViewCountry.setText(response.getJSONObject("sys").getString("country"));
                    textViewDescription.setText(response.getJSONArray("weather").getJSONObject(0).getString("description").toUpperCase());
                    textViewTemperature.setText(( new DecimalFormat("#0.00").format(response.getJSONObject("main").getDouble("temp")-273.15))+"°C");
                    textViewFeels.setText("Feels like "+(new DecimalFormat("#0.00").format(response.getJSONObject("main").getDouble("feels_like")-273.15))+"°C");
                    timezone = response.getLong("timezone");
                    unixDate = response.getLong("dt");
                    textViewTime.setText(new SimpleDateFormat("h:mm a").format(new Date()));
                    textViewDate.setText(new SimpleDateFormat("EEE, d MMM").format(new Date()).toUpperCase());
                    //Toast.makeText(MainActivity.this,response.getJSONArray("weather").getJSONObject(0).getString("icon") , Toast.LENGTH_SHORT).show();
                    Picasso.get().load(Config.WEATHER_IMAGE_URL+response.getJSONArray("weather").getJSONObject(0).getString("icon")+".png").into(imageViewWeather);
                    //imageViewWeather.setImageResource(getResources().getIdentifier(response.getJSONArray("weather").getJSONObject(0).getString("icon"),"drawable",getPackageName()));
                    //Toast.makeText(MainActivity.this, textViewCity.getText(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(MainActivity.this, anError.getErrorCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String unixDateConverter(long unixDate){
        SimpleDateFormat simpleDateFormatDate;
       // long unixSeconds = 1372339860;
// convert seconds to milliseconds
        Date date = new java.util.Date(unixDate*1000L);
// the format of your date
        simpleDateFormatDate = new SimpleDateFormat("EEE, d MMM");
// give a timezone reference for formatting (see comment at the bottom)
        simpleDateFormatDate.setTimeZone(TimeZone.getDefault());//java.util.TimeZone.getTimeZone("GMT-4")
        String formattedDate = simpleDateFormatDate.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }

    private String unixTimeConverter(long unixTime){
        SimpleDateFormat simpleDateFormatTime;
        // long unixSeconds = 1372339860;
// convert seconds to milliseconds
        Date date = new java.util.Date(unixTime*1000L);
// the format of your date
        simpleDateFormatTime = new SimpleDateFormat("h:mm a");
// give a timezone reference for formatting (see comment at the bottom)
        simpleDateFormatTime.setTimeZone(TimeZone.getDefault());//java.util.TimeZone.getTimeZone("GMT-4")
        String formattedDate = simpleDateFormatTime.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }

//
//    public void statusCheck() {
//
//
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps();
//        }else{
//            getCurrentLocation();
//        }
//
//    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            getCurrentLocation();


    }

    @Override
    protected void onResume() {
        super.onResume();

            getCurrentLocation();


    }
    @Override
    protected void onPause() {
        super.onPause();
        if (manager != null)
            manager.removeUpdates(locationListener);
    }

}