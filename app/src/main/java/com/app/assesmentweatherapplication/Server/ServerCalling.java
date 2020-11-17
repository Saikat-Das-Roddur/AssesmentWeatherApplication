package com.app.assesmentweatherapplication.Server;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.assesmentweatherapplication.Config;

public class ServerCalling {


public static void getHourlySevenDaysWeather(String latitude, String longitude, JSONObjectRequestListener listener){
    AndroidNetworking.get(Config.CURRENT_WEATHER_URL)
            .addQueryParameter("lat",latitude)
            .addQueryParameter("lon",longitude)
            .addQueryParameter("appid",Config.API_KEY)
            .build()
            .getAsJSONObject(listener);
}
public static void getHistoricalWeather(String latitude, String longitude,String dt, JSONObjectRequestListener listener){
        AndroidNetworking.get(Config.HISTORICAL_WEATHER_URL)
                .addQueryParameter("lat",latitude)
                .addQueryParameter("lon",longitude)
                .addQueryParameter("dt",dt)
                .addQueryParameter("appid",Config.API_KEY)
                .build()
                .getAsJSONObject(listener);
    }

    public static void getSpecificLocationWeather(String city, JSONObjectRequestListener listener){
    AndroidNetworking.get(Config.SPECIFIC_LOCATION_WEATHER)
            .addQueryParameter("q",city)
            .addQueryParameter("appid",Config.API_KEY)
            .build()
            .getAsJSONObject(listener);
    }

    public static void getCurrentLocationWeather(String latitude, String longitude, JSONObjectRequestListener listener){
        AndroidNetworking.get(Config.SPECIFIC_LOCATION_WEATHER)
                .addQueryParameter("lat",latitude)
                .addQueryParameter("lon",longitude)
                .addQueryParameter("appid",Config.API_KEY)
                .build()
                .getAsJSONObject(listener);
    }
}
