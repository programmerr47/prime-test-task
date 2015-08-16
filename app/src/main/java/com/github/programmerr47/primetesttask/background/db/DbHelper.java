package com.github.programmerr47.primetesttask.background.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.programmerr47.primetesttask.PApplication;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LevelDb.db";

    public static final DbHelper INSTANCE = new DbHelper();

    private DbHelper() {
        super(PApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LevelContract.LevelEntry.SQL_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
