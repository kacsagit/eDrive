package com.example.kata.edrive;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.kata.edrive.fragments.AddPlaceFragment;
import com.example.kata.edrive.fragments.FragmentPager;
import com.example.kata.edrive.fragments.MapFragment;
import com.example.kata.edrive.recycleview.ItemAdapter;
import com.example.kata.edrive.recycleview.RecycleViewItem;

public class MainActivity extends AppCompatActivity implements AddPlaceFragment.IAddPlaceFragment {

    public static ItemAdapter adapter = new ItemAdapter();
    public static ViewPager mainViewPager;
    FragmentPager detailsPagerAdapter;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }else{
            MapFragment.locpermisson=false;
        }
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        detailsPagerAdapter = new FragmentPager(getSupportFragmentManager(),this);
        mainViewPager.setAdapter(detailsPagerAdapter);
    }



    @Override
    public void onNewItemCreated(RecycleViewItem newItem) {
        adapter.addItem(newItem);
    }
}