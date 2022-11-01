package com.example.logbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okio.Timeout;

public class Exercise_2 extends AppCompatActivity {
    ImageView imageView;
    Button back_btn, next_btn, addImageBtn;
    EditText urlImageLink;
    ArrayList<String> listImage;

    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Exercise 2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise2);

        imageView = findViewById(R.id.imageView);
        urlImageLink = findViewById(R.id.urlImageLink);

        listImage = new ArrayList<>();

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listImage.isEmpty()) {
                    Toast.makeText(Exercise_2.this, "No Image!", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Exercise_2.this, "No Image!", Toast.LENGTH_LONG).show();

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
                String img = urlImageLink.getText().toString().trim();
                if (img.length() != 0) {
                    if (URLUtil.isValidUrl(img)) {
                        listImage.add(img);
                        index = listImage.size() - 1;
                        urlImageLink.setText("");
                        display();
                    } else {
                        Toast.makeText(Exercise_2.this, "Url is incorrect!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Exercise_2.this, "Url is null. Please check valid input!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void display() {
        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
        Picasso.get().load(listImage.get(index)).resize(1920, 1080).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(Exercise_2.this, "Url is incorrect!", Toast.LENGTH_LONG).show();
                listImage.remove(index);
                index = listImage.size() - 1;
                display();
            }
        });
    }
}