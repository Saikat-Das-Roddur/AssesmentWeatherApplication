package com.app.assesmentweatherapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.app.assesmentweatherapplication.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(this);
    }
}