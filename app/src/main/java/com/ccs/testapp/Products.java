package com.ccs.testapp;

import android.content.Intent;
import android.os.Bundle;
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

public class Products extends AppCompatActivity {

    private ArrayList<Item> items;
    private ListView listView;
    private ItemAdapter adapter;
    private DatabaseReference databaseRef;
    private int mItemNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        items = new ArrayList<>();
        adapter = new ItemAdapter(this, items);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Инициализируем Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance("https://testapp-15f26-default-rtdb.europe-west1.firebasedatabase.app/").getReference("items");

        // Подтягиваем данные из Firebase Realtime Database
        fetchData();

        Button addButton = findViewById(R.id.add_prod_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.add_prod_btn:
                        // Создаем новый товар
                        Item newItem = new Item(0, items.size() + 1, 0, 0, null);
                        // Добавляем его в список
                        items.add(newItem);
                        // Обновляем список
                        adapter.notifyDataSetChanged();
                        // Получаем уникальный ключ для нового товара в базе данных
                        String itemKey = databaseRef.push().getKey();
                        // Добавляем данные в Firebase Realtime Database
                        databaseRef.child(itemKey).setValue(newItem)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Данные успешно отправлены в базу данных
                                            Log.d("Products", "Data sent to Firebase Realtime Database");
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
                // Вызываем окно для редактирования выбранного товара
                Intent intent = new Intent(Products.this, ProductInfo.class);
                intent.putExtra("position", i);
                intent.putExtra("item", items.get(i).toJson());
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) { // Добавление нового товара
                String itemJson = data.getStringExtra("item");
                Item item = Item.fromJson(itemJson);
                items.add(item);
                adapter.notifyDataSetChanged();
                // Получаем уникальный ключ для нового товара в базе данных
                String itemKey = databaseRef.push().getKey();
                // Добавляем данные в Firebase Realtime Database
                databaseRef.child(itemKey).setValue(item)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Данные успешно отправлены в базу данных
                                    Log.d("Products", "Data sent to Firebase Realtime Database");
                                } else {
                                    // Произошла ошибка при отправке данных
                                    Log.e("Products", "Failed to send data to Firebase Realtime Database: " + task.getException());
                                }
                            }
                        });
            } else if (requestCode == 2) { // Редактирование существующего товара
                int position = data.getIntExtra("position", -1);
                String itemJson = data.getStringExtra("item");
                Item item = Item.fromJson(itemJson);
                if (position != -1) {
                    items.set(position, item);
                    adapter.notifyDataSetChanged();
                    // Обновляем данные в Firebase Realtime Database
                    databaseRef.child(String.valueOf(item.getId())).setValue(item)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Данные успешно обновлены в базе данных
                                        Log.d("Products", "Data updated in Firebase Realtime Database");
                                    } else {
                                        // Произошла ошибка при обновлении данных
                                        Log.e("Products", "Failed to update data in Firebase Realtime Database: " + task.getException());
                                    }
                                }
                            });
                }
            }
        }
    }

    private void fetchData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    if (item != null) {
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
}
