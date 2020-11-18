package com.app.assesmentweatherapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

public class PreviousWeather implements Parcelable {
    String date;
    String Image;
    String temperature;
    String description;
    String sunrise;
    String sunset;
    String humidity;
    String dewPoint;
    String uvi;
    String wind;
    JSONArray hourly;

//    public PreviousWeather(String date, String image, String temperature, String description) {
//        this.date = date;
//        Image = image;
//        this.temperature = temperature;
//        this.description = description;
//    }

    public PreviousWeather(String date, String image, String temperature, String description, String sunrise, String sunset, String humidity, String dewPoint, String uvi, String wind, JSONArray hourly) {
        this.date = date;
        Image = image;
        this.temperature = temperature;
        this.description = description;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.uvi = uvi;
        this.wind = wind;
        this.hourly = hourly;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getUvi() {
        return uvi;
    }

    public void setUvi(String uvi) {
        this.uvi = uvi;
    }

    public JSONArray getHourly() {
        return hourly;
    }

    public void setHourly(JSONArray hourly) {
        this.hourly = hourly;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected PreviousWeather(Parcel in) {
        date = in.readString();
        Image = in.readString();
        temperature = in.readString();
        description = in.readString();
        sunrise = in.readString();
        sunset = in.readString();
        humidity = in.readString();
        dewPoint = in.readString();
        uvi = in.readString();
        wind = in.readString();
        try {
            hourly = in.readByte() == 0x00 ? null : new JSONArray(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(Image);
        dest.writeString(temperature);
        dest.writeString(description);
        dest.writeString(sunrise);
        dest.writeString(sunset);
        dest.writeString(humidity);
        dest.writeString(dewPoint);
        dest.writeString(uvi);
        dest.writeString(wind);
        if (hourly == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(hourly.toString());
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PreviousWeather> CREATOR = new Parcelable.Creator<PreviousWeather>() {
        @Override
        public PreviousWeather createFromParcel(Parcel in) {
            return new PreviousWeather(in);
        }

        @Override
        public PreviousWeather[] newArray(int size) {
            return new PreviousWeather[size];
        }
    };
}