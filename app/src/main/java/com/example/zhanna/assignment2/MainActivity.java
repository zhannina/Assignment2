package com.example.zhanna.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "PREFS";
    Intent intent;
    EditText edit;
    SharedPreferences prefs;
    float mag_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.edit);
        intent = new Intent(this, VibrateService.class);

    }

    @Override
    public void onResume(){
        super.onResume();
        startService(intent);
        prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        mag_value = prefs.getFloat("MAG", 0);

        edit.setText(mag_value + "");

    }


}
