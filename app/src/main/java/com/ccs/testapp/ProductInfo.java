package com.ccs.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductInfo extends AppCompatActivity {
private EditText name, price, amount;
private TextView textInfo;
private Button save, edit;
private ImageView imageView;
private ImageButton upload_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        name = findViewById(R.id.edit_name_prod_inf);
        price = findViewById(R.id.edit_price_prod_inf);
        amount = findViewById(R.id.edit_amount_prod_inf);
        textInfo = findViewById(R.id.text_prod_inf);
        save = findViewById(R.id.save_inf_prod_inf);
        edit = findViewById(R.id.change_inf_prod_inf);
        imageView = findViewById(R.id.im_view_prod_inf);
        upload_btn = findViewById(R.id.im_btn_prod_inf);
        name.setVisibility(View.INVISIBLE);
        price.setVisibility(View.INVISIBLE);
        amount.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        upload_btn.setVisibility(View.INVISIBLE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setVisibility(View.VISIBLE);
                price.setVisibility(View.VISIBLE);
                amount.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                upload_btn.setVisibility(View.VISIBLE);

                textInfo.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getIntent());
                finish();
                overridePendingTransition(0, 0);
            }
        });

    }
}