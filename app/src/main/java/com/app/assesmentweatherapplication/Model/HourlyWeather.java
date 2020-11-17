package com.app.assesmentweatherapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class HourlyWeather implements Parcelable {
    String date;
    String Image;
    String temperature;
    String description;

    public HourlyWeather(String date, String image, String temperature, String description) {
        this.date = date;
        Image = image;
        this.temperature = temperature;
        this.description = description;
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

    protected HourlyWeather(Parcel in) {
        date = in.readString();
        Image = in.readString();
        temperature = in.readString();
        description = in.readString();
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
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HourlyWeather> CREATOR = new Parcelable.Creator<HourlyWeather>() {
        @Override
        public HourlyWeather createFromParcel(Parcel in) {
            return new HourlyWeather(in);
        }

        @Override
        public HourlyWeather[] newArray(int size) {
            return new HourlyWeather[size];
        }
    };
}