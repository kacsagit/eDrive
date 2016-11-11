package com.example.kata.edrive;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static final String ENDPOINT_ADDRESS = "http://ip-api.com";
  //  private static final String APP_ID = "AIzaSyBWr9QKfKRiS-i1i35RxlyPGoGYYYLKJEM";

    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private Retrofit retrofit;
    private StationApi stationApi;

    private NetworkManager() {
        retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_ADDRESS).client(new OkHttpClient.Builder().build()).addConverterFactory(GsonConverterFactory.create()).build();
        stationApi = retrofit.create(StationApi.class);
    }

    public Call<NetworkItem> getData() {
        return stationApi.getData();
    }
}

