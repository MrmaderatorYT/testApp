package com.ccs.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
private Button login, recovery, register;
private EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        recovery = findViewById(R.id.recovery_btn);
        register = findViewById(R.id.register_btn);
        login = findViewById(R.id.login_btn);
        email = findViewById(R.id.edit_em);
        password = findViewById(R.id.edit_pass);
        //подчёркивание текста
        String htmlTaggedString = "<u>Восстановление пароля</u>";
        Spanned textSpan = android.text.Html.fromHtml(htmlTaggedString);
        recovery.setText(textSpan);
        String htmlTaggedString1 = "<u>Регистрация</u>";
        Spanned textSpan1 = android.text.Html.fromHtml(htmlTaggedString1);
        register.setText(textSpan1);
        //переадресация
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String result = email.getText().toString();
                    String result1 = password.getText().toString();
//гуглим,почему нельзя null
                if (result.equals("")||result1.equals("")){
                    Toast.makeText(MainActivity.this, "Введите данные", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, Products.class));
                    overridePendingTransition(0, 0);
                }

            }
        });
        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Recovery.class));
                overridePendingTransition(0, 0);

            }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Registration.class));
                overridePendingTransition(0, 0);

            }
        });

    }


}