package com.github.programmerr47.primetesttask.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class DbUtil {

    public static void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    public static Cursor selectAll(SQLiteDatabase database, String tableName) {
        return database.query(tableName, null, null, null, null, null, null);
    }
}
