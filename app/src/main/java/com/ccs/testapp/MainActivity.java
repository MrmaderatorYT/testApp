package com.ccs.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
private Button login, recovery, register;
private EditText email, password;
private FirebaseAuth auth;
private String em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        recovery = findViewById(R.id.recovery_btn);
        register = findViewById(R.id.register_btn);
        login = findViewById(R.id.login_btn);
        email = findViewById(R.id.edit_em);
        password = findViewById(R.id.edit_pass);
        em = SP.getEm(this);


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
                    auth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;
                            if (user.isEmailVerified()) {
                                String a = result.replace(".", "");
                                String b = a.replace("@", "");
                                SP.em(getApplicationContext(), b);
                                MainActivity.this.startActivity(new Intent(MainActivity.this, Products.class));
                                overridePendingTransition(0, 0);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Проверьте вашу почту для подтверждения вашего адреса", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Что-то пошло не так.", Toast.LENGTH_SHORT).show();

                        }
                        }
                    });

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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        em = SP.getEm(this);
    }
}