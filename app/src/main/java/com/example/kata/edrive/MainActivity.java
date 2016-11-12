package com.example.kata.edrive;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.kata.edrive.fragments.AddPlaceFragment;
import com.example.kata.edrive.fragments.FragmentPager;
import com.example.kata.edrive.fragments.MapFragment;
import com.example.kata.edrive.recycleview.ItemAdapter;
import com.example.kata.edrive.recycleview.RecycleViewItem;

public class MainActivity extends AppCompatActivity implements AddPlaceFragment.IAddPlaceFragment, SensorEventListener {

    public static ItemAdapter adapter = new ItemAdapter();
    public static ViewPager mainViewPager;
    FragmentPager detailsPagerAdapter;
    private SensorManager sm;
    private Sensor sensorSpeed;
    float x = 0;
    float y = 0;
    float z = 0;
    float lastsms=0;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case (0):
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    MapFragment.locpermisson = false;
                }
                return;
            case (123): {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PLAYGROUND", "Permission has been granted");
                } else {
                    Log.d("PLAYGROUND", "Permission has been denied or request cancelled");
                }
                return;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
        } else {
            Log.d("PLAYGROUND", "Permission is granted");
        }

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorSpeed = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        detailsPagerAdapter = new FragmentPager(getSupportFragmentManager(), this);
        mainViewPager.setAdapter(detailsPagerAdapter);
        sm.registerListener(this, sensorSpeed, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onNewItemCreated(RecycleViewItem newItem) {
        adapter.addItem(newItem);
    }


    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.equals(sensorSpeed)) {
            if (event.values[0] - x > 20 || event.values[1] - y > 20 || event.values[2] - z > 20) {
                //Toast.makeText(this, Float.toString(x) + " " + Float.toString(y) + " " + Float.toString(z), Toast.LENGTH_SHORT).show();
            /*    String phoneNumber = "sms:+"+"3620581569355545466643";
                Intent i = new Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse(phoneNumber)
                );
                i.putExtra("sms_body", "The SMS text");
                startActivity(i);*/

                if (System.currentTimeMillis() / 1000-lastsms>=30) {
                    sendSMS("5556", "The SMS text");
                    lastsms=System.currentTimeMillis() / 1000;
                }


            }
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}