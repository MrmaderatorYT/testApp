package com.ccs.testapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Products extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private ArrayList<Item> items;
    String itemKey, em;
    private ListView listView;
    private ItemAdapter adapter;
    private DatabaseReference databaseRef;
    private int mItemNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        em = SP.getEm(this);
        items = new ArrayList<>();
        adapter = new ItemAdapter(this, items);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        // Инициализируем Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance("https://testapp-15f26-default-rtdb.europe-west1.firebasedatabase.app/").getReference("items").getRef().child(em);

        // Подтягиваем данные из Firebase Realtime Database
        fetchData();

        Button addButton = findViewById(R.id.add_prod_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.add_prod_btn:
                        // Создаем новый товар
                        Item newItem = new Item(0, "Товар №" + items.size(), items.size() + 1, 0, 0, null);
                        itemKey = databaseRef.push().getKey(); // Генерируем уникальный ключ для нового товара

                        // Добавляем данные в Firebase Realtime Database
                        databaseRef.child(itemKey).setValue(newItem)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Данные успешно отправлены в базу данных
                                            Log.d("Products", "Data sent to Firebase Realtime Database");

                                            // Обновляем список только после успешной отправки данных
                                            // Данные будут автоматически получены из базы данных в колбэке onDataChange
                                        } else {
                                            // Произошла ошибка при отправке данных
                                            Log.e("Products", "Failed to send data to Firebase Realtime Database: " + task.getException());
                                        }
                                    }
                                });
                        break;
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Получаем выбранный товар
                Item selectedItem = items.get(i);

                // Создаем новый Intent
                Intent intent = new Intent(Products.this, ProductInfo.class);

                // Передаем информацию о выбранном товаре в Intent
                intent.putExtra("number", selectedItem.getNumber());
                intent.putExtra("quantity", selectedItem.getQuantity());
                intent.putExtra("price", selectedItem.getPrice());
                intent.putExtra("id", selectedItem.getId());
                intent.putExtra("itemKey", selectedItem.getItemKey());

                // Запускаем активность ProductInfo с переданными данными
                startActivity(intent);
            }
        });
    }


    private void fetchData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    if (item != null) {
                        item.setImageURL(snapshot.child("imageURL").getValue(String.class));
                        item.setItemKey(snapshot.getKey());

                        items.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
                Log.d("Products", "Data fetched from Firebase Realtime Database");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Произошла ошибка при чтении данных
                Log.e("Products", "Failed to fetch data from Firebase Realtime Database: " + databaseError.getMessage());
            }
        });

    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        em = SP.getEm(this);
    }
}
