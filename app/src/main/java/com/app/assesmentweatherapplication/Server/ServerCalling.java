package com.app.assesmentweatherapplication.Server;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

public class ServerCalling {
public static String CURRENT_WEATHER_URL="https://api.openweathermap.org/data/2.5/onecall";
public static String HISTORICAL_WEATHER_URL="https://api.openweathermap.org/data/2.5/onecall/timemachine";
public static String API_KEY="26b8e6018acdd0fd608048a501871cde";

public static void getCurrentWeather(String latitude, String longitude, JSONObjectRequestListener listener){
    AndroidNetworking.get(CURRENT_WEATHER_URL)
            .addQueryParameter("lat",latitude)
            .addQueryParameter("lon",longitude)
            .addQueryParameter("appid",API_KEY)
            .build()
            .getAsJSONObject(listener);
}
public static void getHistoricalWeather(String latitude, String longitude,String dt, JSONObjectRequestListener listener){
        AndroidNetworking.get(HISTORICAL_WEATHER_URL)
                .addQueryParameter("lat",latitude)
                .addQueryParameter("lon",longitude)
                .addQueryParameter("dt",dt)
                .addQueryParameter("appid",API_KEY)
                .build()
                .getAsJSONObject(listener);
    }
}
