package com.ccs.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Products extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ListView listView = (ListView) findViewById(R.id.listView);
        //ошибка
        DataList prod1 = new DataList("Товар #1", "27 штук","2000");

        ArrayList<DataList> dataList = new ArrayList<>();
        dataList.add(prod1);
        DataList adapter = new DataList(this, R.layout.datalist_activity, dataList);
        listView.setAdapter(adapter);



    }
}