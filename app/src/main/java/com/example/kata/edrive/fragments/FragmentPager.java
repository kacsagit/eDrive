package com.example.kata.edrive.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FragmentPager  extends FragmentPagerAdapter {
        private Context context;

        public FragmentPager(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }          // Ez a függvény csak egyszer hívódik meg, nem fog feleslegesen sok

        // ugyanolyan Fragment-et létrehozni!
        @Override
        public Fragment getItem(int position) {
            Fragment ret = null;
            switch (position) {
                case 0:
                    ret = new ListFragment();


                    break;
                case 1:
                    ret = new MapFragment();

                    break;
            }
            return ret;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title;
            switch (position) {
                case 0:
                    title = "List";
                    break;
                case 1:
                    title = "Map";//context.getString("details");
                    break;
                default:
                    title = "";
            }
            return title;
        }

        @Override
        public int getCount() {
            return 2;
        }

}
