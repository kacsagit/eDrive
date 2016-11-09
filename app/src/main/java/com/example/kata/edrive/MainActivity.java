package com.example.kata.edrive;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kata.edrive.fragments.AddPlaceFragment;
import com.example.kata.edrive.fragments.FragmentPager;
import com.example.kata.edrive.recycleview.ItemAdapter;
import com.example.kata.edrive.recycleview.RecycleViewItem;

public class MainActivity extends AppCompatActivity implements AddPlaceFragment.IAddPlaceFragment {

    public static ItemAdapter adapter = new ItemAdapter();

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

        ViewPager mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        FragmentPager detailsPagerAdapter = new FragmentPager(getSupportFragmentManager(),this);
        mainViewPager.setAdapter(detailsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNewItemCreated(RecycleViewItem newItem) {
        adapter.addItem(newItem);
    }
}
