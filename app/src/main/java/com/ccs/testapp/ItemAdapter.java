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

import com.squareup.picasso.Picasso;

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
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_view);
        TextView nameTextView = convertView.findViewById(R.id.name_text_view);
        TextView quantityTextView = convertView.findViewById(R.id.quantity_text_view);
        TextView priceTextView = convertView.findViewById(R.id.price_text_view);

        Item currentItem = getItem(position);

        // Загружаем и отображаем изображение с помощью Picasso
        Picasso.get()
                .load(currentItem.getImageURL())
                .placeholder(R.drawable.box) // Плейсхолдер, если изображение не загружено
                .error(R.drawable.box) // Изображение ошибки, если загрузка не удалась
                .into(imageView);

        nameTextView.setText(currentItem.getName());
        quantityTextView.setText(String.valueOf(currentItem.getQuantity()));
        priceTextView.setText(String.valueOf(currentItem.getPrice()));

        return convertView;
    }

    public void updateItem(int position, Item newItem) {
        if (position >= 0 && position < mItems.size()) {
            mItems.set(position, newItem);
            notifyDataSetChanged();
        }
    }
}
