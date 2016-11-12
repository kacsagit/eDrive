package com.example.kata.edrive.network;

/**
 * Created by Kata on 2016. 11. 11..
 */
public class GetLocationsEvent<T> {
        private T locations;

        public T getLocations() {
            return locations;
        }

        public void setLocations(T photos) {
            this.locations = photos;
        }

}
