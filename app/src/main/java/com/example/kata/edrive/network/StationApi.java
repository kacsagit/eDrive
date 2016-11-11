package com.example.kata.edrive.network;

import com.example.kata.edrive.network.NetworkItem;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kata on 2016. 11. 11..
 */
public interface StationApi {

    @GET("/json")
    Call<NetworkItem> getData();
}


