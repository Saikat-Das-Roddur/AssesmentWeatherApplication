package com.app.assesmentweatherapplication.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentWeatherAdapter extends RecyclerView.Adapter<CurrentWeatherAdapter.Holder> {
    @NonNull
    @Override
    public CurrentWeatherAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentWeatherAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
