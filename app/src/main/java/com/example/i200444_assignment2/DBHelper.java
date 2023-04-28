package com.example.i200444_assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context) {
        super(context, "Notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists Notes");
    }

    public Boolean addNoteData(String title, String description, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("date", date);
        long result = db.insert("Notes", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }


    public Boolean updateNoteData(String orig_title, String title, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("date", date);
        Cursor cursor = db.rawQuery("Select * from Notes where title = ?", new String[]{orig_title});
        if (cursor.getCount() > 0) {
            long result = db.update("Notes", contentValues, "title=?", new String[]{orig_title});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public Boolean deleteNoteData(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Notes where title = ?", new String[]{title});
        if(cursor.getCount() > 0){
            long result = db.delete("Notes", "title=?", new String[]{title});
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Notes", null);
        return cursor;
    }

    public Cursor getSpecificData(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Notes where title = ?", new String[]{title});
        return cursor;
    }
}
