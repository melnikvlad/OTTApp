package com.example.ottapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ottapp.R;
import com.example.ottapp.data.beans.PopUpItem;

import java.util.ArrayList;

public class PopUpAdapter extends ArrayAdapter<PopUpItem> {

    private Context mContext;

    public PopUpAdapter(Context context, ArrayList<PopUpItem> items) {
        super(context, 0, items);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView,  @NonNull ViewGroup parent) {
        PopUpItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.holder_popup_item, parent, false);
        }

        AppCompatRadioButton radioButton = convertView.findViewById(R.id.radioButton);
        TextView textName = convertView.findViewById(R.id.textName);
        TextView textPrice = convertView.findViewById(R.id.textPrice);

        if (item != null) {
            textName.setText(item.getCompanyName());
            textPrice.setText(String.format(mContext.getString(R.string.price_template_short), item.getSumPrice()));

            if (position == 0) radioButton.setChecked(true);
        }

        return convertView;
    }
}
