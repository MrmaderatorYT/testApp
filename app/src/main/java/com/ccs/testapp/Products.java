package com.ccs.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Products extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private int choosed_block;
    private String USER_KEY="User";
    private DatabaseReference database;
    private Button add;
    private ListView mListView;
    private List<String> listData;
    private ArrayAdapter adapter;
private FirebaseAuth auth;
private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        database = FirebaseDatabase.getInstance("https://testapp-15f26-default-rtdb.europe-west1.firebasedatabase.app/").getReference(USER_KEY);
        FirebaseUser user = auth.getCurrentUser();
        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        mListView = findViewById(R.id.listView);
        add = findViewById(R.id.add_prod_btn);
        choosed_block = PreferencesConfig.loadTotalFromPref(this);
            mListView.setAdapter(adapter);
        getDataFrmDB();
        listData = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listData);
        
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                                 switch (position) {
                                                     case 0:
                                                         choosed_block = 0;
                                                         PreferencesConfig.saveTotalInPref(getApplicationContext(), choosed_block);
                                                         Products.this.startActivity(new Intent(Products.this, ProductInfo.class));
                                                         overridePendingTransition(0, 0);
                                                         break;
                                                     default:
                                                         Toast.makeText(Products.this, "Ошибка", Toast.LENGTH_LONG).show();
                                                         break;


                                                 }

                                             }
                                         });
        //DataListAdapter adapter = new DataListAdapter(this, R.layout.datalist_activity, people);
        //mListView.setAdapter(adapter);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        choosed_block = PreferencesConfig.loadTotalFromPref(this);
    }
    private void getDataFrmDB(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    Data data = ds.getValue(Data.class);
                    listData.add(data.name);
                    listData.add(data.price);
                    listData.add(data.amount);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        database.addValueEventListener(valueEventListener);
    }
}