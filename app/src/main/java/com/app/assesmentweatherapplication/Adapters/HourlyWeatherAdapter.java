package com.app.assesmentweatherapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.assesmentweatherapplication.Config;
import com.app.assesmentweatherapplication.Model.HourlyWeather;
import com.app.assesmentweatherapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.Holder> {
    ArrayList<HourlyWeather> hourlyWeathers = new ArrayList<>();
    Context context;

    public HourlyWeatherAdapter(ArrayList<HourlyWeather> hourlyWeathers, Context context) {
        this.hourlyWeathers = hourlyWeathers;
        this.context = context;
    }

    @NonNull
    @Override
    public HourlyWeatherAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.hourly_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherAdapter.Holder holder, int position) {
        holder.textViewDescription.setText(hourlyWeathers.get(position).getDescription());
        holder.textViewTemperature.setText(hourlyWeathers.get(position).getTemperature());
        holder.textViewDate.setText(hourlyWeathers.get(position).getDate());
        Picasso.get().load(Config.WEATHER_IMAGE_URL+hourlyWeathers.get(position).getImage()).into(holder.imageViewWeather);
    }

    @Override
    public int getItemCount() {
        return hourlyWeathers.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewTemperature, textViewDescription;
        ImageView imageViewWeather;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.dateTv);
            textViewTemperature = itemView.findViewById(R.id.temperatureTv);
            textViewDescription = itemView.findViewById(R.id.descTv);
            imageViewWeather = itemView.findViewById(R.id.weatherIv);
        }
    }
}
