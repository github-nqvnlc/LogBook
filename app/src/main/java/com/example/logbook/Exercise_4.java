package com.example.logbook;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class Exercise_4 extends AppCompatActivity {
    ImageView imageView;
    Button back_btn, next_btn, addImageBtn, openCameraBtn;
    EditText urlImageLink;
    ArrayList<String> listImage;
    ImageButton btnDelete;

    int index;

    Bitmap bitmapImage;
    boolean checkFileBlob = false;
    //connect DB
    ConnectDB connectDB;


    final int CAMERA_INTENT = 51;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Exercise 4");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise4);

        imageView = findViewById(R.id.imageView);
        urlImageLink = findViewById(R.id.urlImageLink);

        listImage = new ArrayList<>();
        index = 0;
        getAllImage();
        if (listImage.isEmpty()) {
            Toast.makeText(this, "No Image in database!", Toast.LENGTH_LONG).show();
        } else {
            display();
        }

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listImage.isEmpty()) {
                    Toast.makeText(Exercise_4.this, "No Image!", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Exercise_4.this, "No Image!", Toast.LENGTH_LONG).show();

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
                ConnectDB connectDB = new ConnectDB(Exercise_4.this);
                String img = urlImageLink.getText().toString().trim();
                if (img.length() != 0) {
                    if (URLUtil.isValidUrl(img)) {
                        Picasso.get().load(img).resize(1920, 1080).into(imageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                connectDB.addImage(img);
                                getAllImage();
                                index = listImage.size() - 1;
                                urlImageLink.setText("");
                                display();
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(Exercise_4.this, "URL Image is incorrect!", Toast.LENGTH_LONG).show();
                                urlImageLink.setText("");
                                display();
                            }
                        });

                    } else {
                        Toast.makeText(Exercise_4.this, "URL Image is incorrect!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Exercise_4.this, "URL Image is null. Please check valid input!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectDB = new ConnectDB(getApplicationContext());
                connectDB.deleteUrlImage(listImage.get(index));
                getAllImage();
                index = listImage.size() - 1;
                display();
            }
        });

        openCameraBtn = findViewById(R.id.openCameraBtn);
        openCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(openCamera);
            }
        });

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(photo);
                        if (photo != null) {
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            byte[] img = bos.toByteArray();
                            String image = java.util.Base64.getEncoder().encodeToString(img);
                            connectDB = new ConnectDB(getApplicationContext());
                            connectDB.addImage(image);
                            getAllImage();
                            index = listImage.size() - 1;
                            display();
                        }
                    }
                }
            });


    void getAllImage() {
        listImage.clear();
        connectDB = new ConnectDB(getApplicationContext());
        Cursor cursor = connectDB.getAllImage();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data!", Toast.LENGTH_LONG).show();
        }
        while (cursor.moveToNext()) {
            listImage.add(cursor.getString(1));
        }

    }


    private void display() {
//        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();

        Picasso.get().load(listImage.get(index)).resize(1920, 1080).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                String img = listImage.get(index);
                byte[] theByteArray = Base64.getDecoder().decode(img);
                Bitmap image = BitmapFactory.decodeByteArray(theByteArray, 0, theByteArray.length);
                if (image != null) {
                    imageView.setImageBitmap(Bitmap.createScaledBitmap(image, 1920, 1080, false));
                } else {
                    Toast.makeText(Exercise_4.this, "URL Image is incorrect!", Toast.LENGTH_LONG).show();
                    connectDB = new ConnectDB(getApplicationContext());
                    connectDB.deleteUrlImage(listImage.get(index));
                    index = listImage.size() - 1;
                    display();
                    
                }

            }
        });
    }


}