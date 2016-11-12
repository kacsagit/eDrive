package com.example.kata.edrive.network;


import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static final String ENDPOINT_ADDRESS = "http://ip-api.com";
  //  private static final String APP_ID = "AIzaSyBWr9QKfKRiS-i1i35RxlyPGoGYYYLKJEM";

    private static NetworkManager instance;

    private final Context context;

    private Retrofit retrofit;
    private StationApi stationApi;

    public NetworkManager(Context context) {
            this.context = context;

            retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_ADDRESS).client(new OkHttpClient.Builder().build()).addConverterFactory(GsonConverterFactory.create()).build();
            stationApi = retrofit.create(StationApi.class);
    }

    private static <T> void runCallOnBackgroundThread(final Call<T> call) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final T locations = call.execute().body();
                    GetLocationsEvent getLocationEvent=new GetLocationsEvent();
                    getLocationEvent.setLocations(locations);
                    EventBus.getDefault().post(getLocationEvent);

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getData() {

        Call<NetworkItem> getLocationRequest = stationApi.getData();
        runCallOnBackgroundThread(getLocationRequest);
    }

    public void uploadPhoto(Uri fileUri) {
       /* File file = new File(fileUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse(GalleryAPI.MULTIPART_FORM_DATA), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(GalleryAPI.PHOTO_MULTIPART_KEY_IMG, file.getName(), requestFile);

        Call<ResponseBody> uploadPhotoRequest = galleryApi.uploadPhoto(body);
        runCallOnBackgroundThread(uploadPhotoRequest);*/
    }

    public interface ResponseListener<T> {
        void onResponse(T t);
    }
}

