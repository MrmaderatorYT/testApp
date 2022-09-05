package com.ccs.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
private Button reg;
private EditText email, pass1, pass2;
private DatabaseReference database;
private String USER_KEY="User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance("https://testapp-15f26-default-rtdb.europe-west1.firebasedatabase.app/").getReference(USER_KEY);
        reg = findViewById(R.id.send_pass_btn);
        email = findViewById(R.id.edit_em_reg);
        pass1 = findViewById(R.id.edit_pass_reg);
        pass2 = findViewById(R.id.edit_pass_2_reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String id = database.getKey();
            String em = email.getText().toString();
            String pass = pass1.getText().toString();
            String pass_1 = pass2.getText().toString();
            if(TextUtils.isEmpty(em) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass_1)){

                Toast.makeText(Registration.this, "Введите все данные.", Toast.LENGTH_SHORT).show();
            }
            else{
                //if err{rem pass1.eq(pass)
                if(pass.equals(pass_1)||pass_1.equals(pass)){
                    User newUser = new User(id, em, pass);
                    database.push().setValue(newUser);
                    Toast.makeText(Registration.this, "Зарегистрировано!", Toast.LENGTH_SHORT).show();
                }   else {

                    Toast.makeText(Registration.this, "Пароли не совпадают.", Toast.LENGTH_SHORT).show();

                }
            }


            Log.i("Registration", "Firebase");

            }
        });

    }
}