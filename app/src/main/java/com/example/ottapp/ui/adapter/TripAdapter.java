package com.example.ottapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ottapp.R;
import com.example.ottapp.data.source.local.model.UITripEntity;

import java.util.ArrayList;
import java.util.List;


public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private final Context mContext;
    private final OnHotelClickListener mCallback;
    private List<UITripEntity> mList = new ArrayList<>();

    public TripAdapter(Context context, OnHotelClickListener callback) {
        mContext = context;
        mCallback = callback;
    }

    @NonNull
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holder_rv_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.ViewHolder holder, int i) {
        int pos = holder.getAdapterPosition();
        holder.bind(pos);
        holder.card.setOnClickListener(v -> mCallback.onItemClick(pos, mList.get(pos)));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void refresh(final List<UITripEntity> list) {
        if (!mList.isEmpty()) mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View card;
        TextView textName;
        TextView textFlightsCount;
        TextView textPrice;

        ViewHolder(@NonNull View v) {
            super(v);
            card = v.findViewById(R.id.parent);
            textName = v.findViewById(R.id.text_name);
            textFlightsCount = v.findViewById(R.id.text_flights_count);
            textPrice = v.findViewById(R.id.text_lowest_price);
        }

        void bind(int pos) {
            UITripEntity item = mList.get(pos);

            if (item != null) {
                textName.setText(String.format(mContext.getString(R.string.hotel_template), item.getHotelName()));
                textFlightsCount.setText(String.format(mContext.getString(R.string.count_template), item.getFlights().size()));
                textPrice.setText(String.format(mContext.getString(R.string.price_template), item.getTotalMinPrice()));
            }
        }
    }

    public interface OnHotelClickListener {
        void onItemClick(int pos, UITripEntity item);
    }
}
