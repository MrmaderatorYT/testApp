package com.ccs.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddData extends AppCompatActivity {
private EditText name, price, amount;
private Button create;
private DatabaseReference database;
public String USER_KEY="User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        database = FirebaseDatabase.getInstance("https://testapp-15f26-default-rtdb.europe-west1.firebasedatabase.app/").getReference(USER_KEY);
        name = findViewById(R.id.edit_name);
        price = findViewById(R.id.edit_price);
        amount = findViewById(R.id.edit_amount);
        String name1 = name.getText().toString().trim();
        String price1 = price.getText().toString().trim();
        String amount1 = amount.getText().toString().trim();


    }
}