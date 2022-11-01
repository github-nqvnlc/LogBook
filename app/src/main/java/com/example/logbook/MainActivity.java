package com.example.logbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnEx1, btnEx2, btnEx3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEx1 = findViewById(R.id.btnEx1);
        btnEx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Exercise_1.class);
                startActivity(intent);
            }
        });

        btnEx2 = findViewById(R.id.btnEx2);
        btnEx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Exercise_2.class);
                startActivity(intent);
            }
        });

        btnEx3 = findViewById(R.id.btnEx3);
        btnEx3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Exercise_3.class);
                startActivity(intent);
            }
        });
    }
}