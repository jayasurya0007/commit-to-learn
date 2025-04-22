package com.example.librarymgmtsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    public DB(Context context) {
        super(context, "Library", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table bookinsert(BookName TEXT primary key, Author TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists bookinsert");
    }

    // Save method to insert data
    public void save(String bookName, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BookName", bookName);
        contentValues.put("Author", author);
        db.insert("bookinsert", null, contentValues);
    }

    // Retrieve method to get the author based on book name
    public String retrieve(String bookName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from bookinsert where BookName = ?", new String[]{bookName});
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("Author"));
        } else {
            return "Book not found";
        }
    }

    // Update method to change book author
    public void update(String bookName, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Author", author);
        db.update("bookinsert", contentValues, "BookName = ?", new String[]{bookName});
    }

    // Delete method to remove a book from the database
    public void delete(String bookName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("bookinsert", "BookName = ?", new String[]{bookName});
    }
}
