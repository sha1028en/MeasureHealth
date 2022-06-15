/*
package com.gadimai.measurehealth.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gadimai.measurehealth.Utill.UsefulUtil;


public class DBopenHelper extends SQLiteOpenHelper {
    private UsefulUtil mUsefulUtil;


    public DBopenHelper(Context context, String name, SQLiteDatabase.CursorFactory Cursor, int ver){
        super(context, name, Cursor, ver);
        mUsefulUtil = new UsefulUtil(context);

        mUsefulUtil.forDebugLog("");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


*/