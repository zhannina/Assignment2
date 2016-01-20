package com.example.zhanna.assignment2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import static android.os.Vibrator.*;

public class VibrateService extends Service implements SensorEventListener{
    public static final String PREFS = "PREFS";
    SensorManager sm;
    Sensor magSensor;

    long[] pattern = {0, 1000, 10 };
    public VibrateService() {

    }

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        magSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sm.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Log.d("service", "created");

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        stopSelf();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor s = event.sensor;
        if (s.getType()==Sensor.TYPE_MAGNETIC_FIELD);

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float mag_value = (float) Math.sqrt(x*x+y*y+z*z);
        prefs = getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        editor = prefs.edit();

        editor.putFloat("MAG", mag_value);
        editor.commit();


        if (mag_value > 35){

            // Vibrate for 400 milliseconds
            v.vibrate(pattern, 0);
        } else {
            v.cancel();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing
    }
}
