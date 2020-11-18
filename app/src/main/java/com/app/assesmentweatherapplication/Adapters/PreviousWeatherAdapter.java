package com.app.assesmentweatherapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.assesmentweatherapplication.Config;
import com.app.assesmentweatherapplication.Model.PreviousWeather;
import com.app.assesmentweatherapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PreviousWeatherAdapter extends RecyclerView.Adapter<PreviousWeatherAdapter.Holder> {

    ArrayList<PreviousWeather> previousWeathers = new ArrayList<>();
    Context context;

    public PreviousWeatherAdapter(ArrayList<PreviousWeather> previousWeathers, Context context) {
        this.previousWeathers = previousWeathers;
        this.context = context;
    }

    @NonNull
    @Override
    public PreviousWeatherAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.previous_five_days_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousWeatherAdapter.Holder holder, int position) {
//        for (int i = 0; i < previousWeathers.size(); i++) {
//            if (previousWeathers.get(i).getDate().contains(previousWeathers.get(position).getDate())){
//                Toast.makeText(context, "got date: "+i, Toast.LENGTH_SHORT).show();
//            }
//        }
       // Toast.makeText(context, previousWeathers.size()+"", Toast.LENGTH_SHORT).show();
                holder.textViewDate.setText(previousWeathers.get(position).getDate());
                holder.textViewDescription.setText(previousWeathers.get(position).getDescription());
                holder.textViewTemperature.setText(previousWeathers.get(position).getTemperature());
                Picasso.get().load(Config.WEATHER_IMAGE_URL+previousWeathers.get(position).getImage()).into(holder.imageViewWeather);





      }

    @Override
    public int getItemCount() {
        return previousWeathers.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewTemperature,textViewDescription;
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
