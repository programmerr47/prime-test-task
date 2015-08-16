package com.github.programmerr47.primetesttask.background.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.programmerr47.primetesttask.background.api.objects.LevelInfo;
import com.github.programmerr47.primetesttask.util.DbUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class LevelInfoDao {

    public static List<LevelInfo> getAllLevelsInfos() {
        SQLiteDatabase database = DbHelper.INSTANCE.getReadableDatabase();
        Cursor cursor = DbUtil.selectAll(database, LevelContract.LevelEntry.TABLE_NAME);
        List<LevelInfo> result = getListFromCursor(cursor);
        DbUtil.closeCursor(cursor);
        return result;
    }

    public static void saveLevels(List<LevelInfo> savingList) {
        SQLiteDatabase database = DbHelper.INSTANCE.getWritableDatabase();
        for (LevelInfo savingEntry : savingList) {
            ContentValues contentValues = getContentValuesFromLevelInfo(savingEntry);

            if (hasEntry(database, savingEntry.getLevel())) {
                database.update(
                        LevelContract.LevelEntry.TABLE_NAME,
                        contentValues,
                        LevelContract.LevelEntry.COLUMN_NAME_LEVEL_NUMBER + "=?",
                        new String[]{String.valueOf(savingEntry.getLevel())}
                );
            } else {
                database.insert(
                        LevelContract.LevelEntry.TABLE_NAME,
                        null,
                        contentValues
                );
            }
        }
    }

    private static boolean hasEntry(SQLiteDatabase database, int levelNumber) {
        Cursor cursor = database.query(
                LevelContract.LevelEntry.TABLE_NAME, null,
                LevelContract.LevelEntry.COLUMN_NAME_LEVEL_NUMBER + "=?",
                new String[] {String.valueOf(levelNumber)},
                null, null, null);

        boolean answer = false;
        if (cursor != null) {
            answer = cursor.moveToFirst();
            cursor.close();
        }
        return answer;
    }

    private static List<LevelInfo> getListFromCursor(Cursor cursor) {
        List<LevelInfo> result = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                result.add(getSingleFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return result;
    }

    private static LevelInfo getSingleFromCursor(Cursor cursor) {
        return new LevelInfo.Builder()
                .setLevel(cursor.getInt(cursor.getColumnIndex(LevelContract.LevelEntry.COLUMN_NAME_LEVEL_NUMBER)))
                .setPointsToGain(cursor.getInt(cursor.getColumnIndex(LevelContract.LevelEntry.COLUMN_NAME_POINTS_TO_GAIN)))
                .setPointsPerClick(cursor.getInt(cursor.getColumnIndex(LevelContract.LevelEntry.COLUMN_NAME_POINTS_PER_CLICK)))
                .build();
    }

    private static ContentValues getContentValuesFromLevelInfo(LevelInfo levelInfo) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(LevelContract.LevelEntry.COLUMN_NAME_LEVEL_NUMBER, levelInfo.getLevel());
        contentValues.put(LevelContract.LevelEntry.COLUMN_NAME_POINTS_TO_GAIN, levelInfo.getPointsToGain());
        contentValues.put(LevelContract.LevelEntry.COLUMN_NAME_POINTS_PER_CLICK, levelInfo.getPointsPerClick());

        return contentValues;
    }
}
