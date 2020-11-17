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
import com.app.assesmentweatherapplication.Model.SevenDaysWeather;
import com.app.assesmentweatherapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SevenDaysWeatherAdapter extends RecyclerView.Adapter<SevenDaysWeatherAdapter.Holder> {
    ArrayList<SevenDaysWeather> sevenDaysWeathers = new ArrayList<>();
    Context context;

    public SevenDaysWeatherAdapter(ArrayList<SevenDaysWeather> sevenDaysWeathers, Context context) {
        this.sevenDaysWeathers = sevenDaysWeathers;
        this.context = context;
    }

    @NonNull
    @Override
    public SevenDaysWeatherAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.seven_days_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SevenDaysWeatherAdapter.Holder holder, int position) {
        holder.textViewDate.setText(sevenDaysWeathers.get(position).getDate());
        holder.textViewTemperature.setText(sevenDaysWeathers.get(position).getTempMax()+"°/"
        +sevenDaysWeathers.get(position).getTempMin()+"°");
        Picasso.get().load(Config.WEATHER_IMAGE_URL+sevenDaysWeathers.get(position)
                .getImage()).into(holder.imageViewWeather);
    }

    @Override
    public int getItemCount() {
        return sevenDaysWeathers.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textViewDate,textViewTemperature;
        ImageView imageViewWeather;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.dateTv);
            textViewTemperature = itemView.findViewById(R.id.temperatureTv);
            imageViewWeather = itemView.findViewById(R.id.weatherIv);
        }
    }
}
