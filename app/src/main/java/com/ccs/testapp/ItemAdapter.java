package com.ccs.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private ArrayList<Item> mItems;

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        mContext = context;
        mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.listview, parent, false);
        }

        Item item = mItems.get(position);

        TextView nameTextView = view.findViewById(R.id.name_text_view);
        nameTextView.setText(String.valueOf(item.getNumber()));

        TextView quantityTextView = view.findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(item.getQuantity()));

        TextView priceTextView = view.findViewById(R.id.price_text_view);
        priceTextView.setText(String.valueOf(item.getPrice()));

        return view;
    }
}
