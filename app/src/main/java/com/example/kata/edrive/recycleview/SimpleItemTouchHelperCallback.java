package com.example.kata.edrive.recycleview;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.kata.edrive.recycleview.ItemAdapter;

/**
 * Created by Kata on 2016. 11. 09..
 */


//https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf#.qsdo56fot

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemAdapter mAdapter;
    public RecyclerView recyclerView;

    public SimpleItemTouchHelperCallback(ItemAdapter adapter, RecyclerView rv) {
        mAdapter = adapter;
        recyclerView = rv;

    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        final int pos=viewHolder.getAdapterPosition();
     //   mAdapter.onItemDismiss(viewHolder.getAdapterPosition());

        Snackbar.make(recyclerView, "If didn't want to delete", Snackbar.LENGTH_LONG)
                .setCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        switch (event) {
                            case Snackbar.Callback.DISMISS_EVENT_ACTION:
                                mAdapter.notifyItemRangeChanged(pos, mAdapter.getItemCount());
                                break;
                            default:
                                mAdapter.onItemDismiss(pos);
                                break;
                        }
                    }
                }).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).setActionTextColor(Color.RED).show();
    }


}