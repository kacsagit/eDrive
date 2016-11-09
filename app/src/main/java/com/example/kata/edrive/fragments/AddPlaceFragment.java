package com.example.kata.edrive.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.kata.edrive.R;
import com.example.kata.edrive.recycleview.RecycleViewItem;

public class AddPlaceFragment extends AppCompatDialogFragment {
    public static final String TAG = "AddPlaceFragment";

    private EditText place;
    private EditText longitude;
    private EditText latitude;

    private IAddPlaceFragment listener;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof IAddPlaceFragment) {
            listener = (IAddPlaceFragment) activity;
        } else {
            throw new RuntimeException("Activity must implement the IAddPlaceFragment interface!");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.new_item)
                .setView(getContentView())
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isValid()) {
                                    listener.onNewItemCreated(getShoppingItem());
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    public interface IAddPlaceFragment {
        void onNewItemCreated(RecycleViewItem newItem);
    }

    private boolean isValid() {
        return place.getText().length() > 0;
    }

    private RecycleViewItem getShoppingItem() {
        RecycleViewItem item = new RecycleViewItem();
        item.place = place.getText().toString();
        item.latitude = Double.parseDouble(latitude.getText().toString());
        item.longitude = Double.parseDouble(longitude.getText().toString());

        return item;
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_place, null);
        place= (EditText) contentView.findViewById(R.id.PlaceEditText);
        longitude = (EditText) contentView.findViewById(R.id.LongitudeEditText);
        latitude= (EditText) contentView.findViewById(R.id.LatitudeEditText);
        return contentView;
    }

}
