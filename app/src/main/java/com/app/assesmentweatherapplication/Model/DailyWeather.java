package com.app.assesmentweatherapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class DailyWeather implements Parcelable {
    String date;
    String tempMin;
    String tempMax;
    String image;

    public DailyWeather(String date, String tempMin, String tempMax, String image) {
        this.date = date;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    protected DailyWeather(Parcel in) {
        date = in.readString();
        tempMin = in.readString();
        tempMax = in.readString();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(tempMin);
        dest.writeString(tempMax);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DailyWeather> CREATOR = new Parcelable.Creator<DailyWeather>() {
        @Override
        public DailyWeather createFromParcel(Parcel in) {
            return new DailyWeather(in);
        }

        @Override
        public DailyWeather[] newArray(int size) {
            return new DailyWeather[size];
        }
    };
}