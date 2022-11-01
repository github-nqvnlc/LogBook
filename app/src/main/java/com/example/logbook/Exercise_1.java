package com.example.logbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Exercise_1 extends AppCompatActivity {
    ImageView imageView;
    Button back_btn, next_btn;
    ArrayList<String> listImage;
    int index;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Exercise 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);

        imageView = findViewById(R.id.imageView);
        back_btn = findViewById(R.id.back_btn);
        next_btn = findViewById(R.id.next_btn);


        listImage = new ArrayList<>();
        listImage.add("https://i1-dulich.vnecdn.net/2022/09/14/2-1663172227.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=8FXVq7zh1XDTSXe3kKiT0A");
        listImage.add("https://i1-dulich.vnecdn.net/2022/09/14/4-1663171508.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=S06yaNXNAEw5yuUNPbsJYA");
        listImage.add("https://i1-dulich.vnecdn.net/2022/09/14/3-1663171512.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=pGb0siV5c9GISnpLqF62SA");
        listImage.add("https://i1-dulich.vnecdn.net/2022/09/14/7-1663171465.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=h8Le5dwLusXpKrmsp_op5A");
        listImage.add("https://i1-dulich.vnecdn.net/2022/09/14/5-1663171484.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=64DohDtfD0IlG34SV2MkbQ");
        listImage.add("https://i1-dulich.vnecdn.net/2022/09/14/10-1663171428.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=XYJnlOeVoyzmf6_pGLqhbw");
        index = 1;
        display();

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index == 0) {
                    index = listImage.size() - 1;
                } else {
                    index -= 1;
                }
                display();
            }
        });

        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexOld = listImage.size() -1;
                if (index == indexOld) {
                    index = 0;
                } else {
                    index += 1;
                }
                display();
            }
        });
    }

    private void display() {
        Picasso.get().load(listImage.get(index)).resize(1920, 1080).into(imageView);
    }
}