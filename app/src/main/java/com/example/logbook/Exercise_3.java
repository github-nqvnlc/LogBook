package com.example.logbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Exercise_3 extends AppCompatActivity {
    ImageView imageView;
    Button back_btn, next_btn, addImageBtn;
    EditText urlImageLink;
    ArrayList<String> listImage;

    int index;

    //connect DB
    ConnectDB connectDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Exercise 3");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise3);

        imageView = findViewById(R.id.imageView);
        back_btn = findViewById(R.id.back_btn);
        next_btn = findViewById(R.id.next_btn);
        urlImageLink = findViewById(R.id.urlImageLink);

        listImage = new ArrayList<>();
        getAllImage();
        display();

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listImage.isEmpty()) {
                    Toast.makeText(Exercise_3.this, "No Image!", Toast.LENGTH_LONG).show();
                } else {
                    if (index == 0) {
                        index = listImage.size() - 1;
                    } else {
                        index -= 1;
                    }
                    display();
                }
            }
        });

        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listImage.isEmpty()) {
                    Toast.makeText(Exercise_3.this, "No Image!", Toast.LENGTH_LONG).show();

                } else {
                    int indexOld = listImage.size() - 1;
                    if (index == indexOld) {
                        index = 0;
                    } else {
                        index += 1;
                    }
                    display();
                }
            }
        });

        addImageBtn = findViewById(R.id.addImageBtn);
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectDB connectDB = new ConnectDB(Exercise_3.this);
                String img = urlImageLink.getText().toString().trim();
                if (img.length() != 0) {
                    if (URLUtil.isValidUrl(img)) {
                        connectDB.addImage(img);
                        getAllImage();
                        index = listImage.size() - 1;
                        urlImageLink.setText("");
                        display();
                    } else {
                        Toast.makeText(Exercise_3.this, "URL Image is incorrect!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Exercise_3.this, "URL Image is null. Please check valid input!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void getAllImage() {
        listImage.clear();
        connectDB = new ConnectDB(getApplicationContext());
        Cursor cursor = connectDB.getAllImage();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data!", Toast.LENGTH_LONG).show();
        }
        while (cursor.moveToNext()) {
            listImage.add(cursor.getString(1));
        }

    }

    private void display() {
        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
        Picasso.get().load(listImage.get(index)).resize(1920, 1080).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(Exercise_3.this, "URL Image is incorrect!", Toast.LENGTH_LONG).show();
            }
        });
    }
}