package com.example.kata.edrive.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kata.edrive.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kata on 2016. 11. 09..
 */
public class ItemAdapter extends
        RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final List<RecycleViewItem> items;

    public ItemAdapter() {
        items = new ArrayList<>();
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_recyclerview, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        RecycleViewItem item = items.get(position);
        holder.place.setText(item.place);
        holder.longitude.setText(Double.toString(item.longitude));
        holder.latitude.setText(Double.toString(item.latitude));
        holder.latitude1.setText(R.string.latitude);
        holder.longitude1.setText(R.string.longitude);
        holder.place1.setText(R.string.place);
    }

    public void addItem(RecycleViewItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void onItemDismiss(int position) {
        RecycleViewItem removed = items.remove(position);
        removed.delete();
        notifyItemRemoved(position);
        if (position < items.size()) {
            notifyItemRangeChanged(position, items.size() - position);
        }
    }


    public void onItemMove(int fromPosition, int toPosition) {
        RecycleViewItem prev = items.remove(fromPosition);
        items.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);


    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void update(List<RecycleViewItem> items) {
            items.clear();
            items.addAll(items);
            notifyDataSetChanged();

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView place1;
        TextView longitude1;
        TextView latitude1;
        TextView place;
        TextView longitude;
        TextView latitude;


        public ItemViewHolder(View itemView) {
            super(itemView);
            place = (TextView) itemView.findViewById(R.id.PlaceName);
            longitude = (TextView) itemView.findViewById(R.id.PlaceLongitude);
            latitude = (TextView) itemView.findViewById(R.id.PlaceLatitude);
            place1 = (TextView) itemView.findViewById(R.id.Place);
            longitude1 = (TextView) itemView.findViewById(R.id.Longitude);
            latitude1 = (TextView) itemView.findViewById(R.id.Latitude);

        }
    }
}
