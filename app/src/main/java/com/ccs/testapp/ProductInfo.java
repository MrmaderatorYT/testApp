package com.ccs.testapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ProductInfo extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    private EditText name, price, amount;
    private TextView textInfo;
    private Button save, edit;
    private ImageView imageView;
    private ImageButton upload_btn;
    private int number;
    int id;
    private int quantity;
    private double priceInt;
    private Uri selectedImageUri; // URI выбранного изображения

    private DatabaseReference databaseRef;
    private StorageReference storageRef;
    private ItemAdapter itemAdapter;
    String itemKey, em;
    private static final int PICK_IMAGE_REQUEST = 1;

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
        em = SP.getEm(this);
        databaseRef = FirebaseDatabase.getInstance("https://testapp-15f26-default-rtdb.europe-west1.firebasedatabase.app/").getReference("items").child(em);
        storageRef = FirebaseStorage.getInstance("gs://testapp-15f26.appspot.com").getReference("images");
        Intent intent = getIntent();
        number = intent.getIntExtra("number", 0);
        quantity = intent.getIntExtra("quantity", 0);
        priceInt = intent.getDoubleExtra("price", 0.0);
        itemKey = getIntent().getStringExtra("itemKey");

        // Устанавливаем значения в соответствующие поля
        name.setText(intent.getStringExtra("name"));
        price.setText(String.valueOf(priceInt));
        amount.setText(String.valueOf(quantity));

        textInfo.setText("Номер товара: " + number + "\n" + "Количество: " + quantity + "\n" + "Цена: " + priceInt + " грн");
        databaseRef.child(itemKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imageURL = dataSnapshot.child("imageURL").getValue(String.class);
                Picasso.get()
                        .load(imageURL)
                        .into(imageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Произошла ошибка при чтении данных
                Log.e("ProductInfo", "Failed to fetch image URL from Firebase Realtime Database: " + databaseError.getMessage());
            }
        });

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

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                upload_btn.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveItem() {
        String itemName = name.getText().toString();
        double itemPrice = Double.parseDouble(price.getText().toString());
        int itemQuantity = Integer.parseInt(amount.getText().toString());

        // Генерируем уникальное имя для изображения
        String imageFileName = UUID.randomUUID().toString();


        // Сжимаем изображение и сохраняем в Firebase Storage
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] imageData = baos.toByteArray();

            StorageReference imageRef = storageRef.child(imageFileName);
            UploadTask uploadTask = imageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageURL = uri.toString();

                                    // Создаем экземпляр класса Item с передачей значения id и URL картинки
                                    Item item = new Item(id, itemName, number, itemQuantity, itemPrice, imageURL);
                                    if (itemKey != null) {
                                        // Сохраняем в базе данных
                                        databaseRef.child(itemKey).setValue(item)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Успешно сохранено в базе данных
                                                        Toast.makeText(ProductInfo.this, "доне", Toast.LENGTH_SHORT).show();

                                                        // Обновляем соответствующий элемент списка с новой информацией о картинке
                                                        if (itemAdapter != null) {
                                                            Item updatedItem = new Item(id, itemName, number, itemQuantity, itemPrice, imageURL);
                                                            itemAdapter.updateItem(id, updatedItem);
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Ошибка при сохранении в базе данных
                                                        Toast.makeText(ProductInfo.this, "не доне", Toast.LENGTH_SHORT).show();
                                                        Log.e("ProductInfo", "Failed to save item to Firebase Realtime Database: " + e.getMessage());
                                                    }
                                                });
                                    }
                                    else{
                                        Log.e("ProductInfo", "itemKey is null");
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Ошибка при загрузке изображения в Firebase Storage
                            Toast.makeText(ProductInfo.this, "не доне", Toast.LENGTH_SHORT).show();
                            Log.e("ProductInfo", "Failed to upload image to Firebase Storage: " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        em = SP.getEm(this);
    }

}
