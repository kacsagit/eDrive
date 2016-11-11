package com.example.kata.edrive.fragments;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kata.edrive.MainActivity;
import com.example.kata.edrive.network.NetworkItem;
import com.example.kata.edrive.network.NetworkManager;
import com.example.kata.edrive.R;
import com.example.kata.edrive.recycleview.RecycleViewItem;
import com.example.kata.edrive.recycleview.SimpleItemTouchHelperCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements AddPlaceFragment.IAddPlaceFragment{ //implements AddPlaceFragment.IAddPlaceFragment{

    private static final Object TAG = "";
    private RecyclerView recyclerView;
    //public ItemAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;
    View view;
    SwipeRefreshLayout swipeRefresh;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddPlaceFragment().show(myContext.getSupportFragmentManager(),AddPlaceFragment.TAG);
            }
        });
        initRecycleView();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadWebData();


    }


    public FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


    private void initRecycleView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.MainRecyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(MainActivity.adapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebData();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(MainActivity.adapter,recyclerView);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<RecycleViewItem>>() {

            @Override
            protected List<RecycleViewItem> doInBackground(Void... voids) {
                return RecycleViewItem.listAll(RecycleViewItem.class);
            }

            @Override
            protected void onPostExecute(List<RecycleViewItem> shoppingItems) {
                super.onPostExecute(shoppingItems);
                MainActivity.adapter.update(shoppingItems);
            }
        }.execute();
    }


    private void loadWebData() {
        NetworkManager.getInstance().getData().enqueue(new Callback<NetworkItem>() {
            @Override
            public void onResponse(Call<NetworkItem> call, Response<NetworkItem> response) {
                if (response.isSuccessful()) {
                    RecycleViewItem item=new RecycleViewItem();
                    item.latitude=response.body().lat;
                    item.longitude=response.body().lon;
                    item.place=response.body().city;
                    MainActivity.adapter.addItem(item);
                    swipeRefresh.setRefreshing(false);

                } else {
                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NetworkItem> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Error in network request", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onNewItemCreated(RecycleViewItem newItem) {
        MainActivity.adapter.addItem(newItem);
    }




}