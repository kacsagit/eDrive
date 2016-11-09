package com.example.kata.edrive.fragments;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kata.edrive.recycleview.ItemAdapter;
import com.example.kata.edrive.MainActivity;
import com.example.kata.edrive.R;
import com.example.kata.edrive.recycleview.RecycleViewItem;
import com.example.kata.edrive.recycleview.SimpleItemTouchHelperCallback;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment{ //implements AddPlaceFragment.IAddPlaceFragment{

    private static final Object TAG = "";
    private RecyclerView recyclerView;
    //public ItemAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;
    View view;

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
    }


    public FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


    private void initRecycleView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.MainRecyclerView);
        MainActivity.adapter = new ItemAdapter();
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(MainActivity.adapter);

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


  /*  @Override
    public void onNewItemCreated(RecycleViewItem newItem) {
        adapter.addItem(newItem);
    }*/




}
