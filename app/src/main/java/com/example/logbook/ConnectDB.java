package com.example.logbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class ConnectDB extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Image.db";
    private static final String TABLE_IMAGE = "images";

    public ConnectDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String image_table = "CREATE TABLE " + TABLE_IMAGE + "(_id INTEGER primary key autoincrement, urlImage TEXT)";
        db.execSQL(image_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGE);
        onCreate(db);
    }

    void addImage(String urlImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("urlImage", urlImage);

        long result = db.insert(TABLE_IMAGE, null, cv);
        if(result ==-1){
            Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Add image successful", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllImage() {
        String queryDB = "SELECT _id, urlImage FROM " + TABLE_IMAGE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(queryDB, null);
        }
        return cursor;
    }

    public void deleteUrlImage(String urlImage){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_IMAGE, "urlImage=?", new String[]{urlImage});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete Url Image is successful", Toast.LENGTH_SHORT).show();
        }
    }
}
