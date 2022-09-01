package com.ccs.testapp;

import android.app.Person;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DataList extends ArrayAdapter<Data> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView number;
        TextView amount;
        TextView price;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public DataList(Context context, int resource, ArrayList<Data> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String number = getItem(position).getNumber();
        String amount = getItem(position).getAmount();
        String price = getItem(position).getPrice();

        //Create the person object with the information
        Data data = new Data(number,amount,price);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.textView1);
            holder.amount = (TextView) convertView.findViewById(R.id.textView2);
            holder.price = (TextView) convertView.findViewById(R.id.textView3);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }




        holder.number.setText(data.getNumber());
        holder.amount.setText(data.getAmount());
        holder.price.setText(data.getPrice());


        return convertView;
    }

}
