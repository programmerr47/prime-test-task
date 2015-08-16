package com.github.programmerr47.primetesttask.background.db;

import android.provider.BaseColumns;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
class LevelContract {

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ", ";

    public static abstract class LevelEntry implements BaseColumns {
        public static final String TABLE_NAME = "level";
        public static final String COLUMN_NAME_LEVEL_NUMBER = "levelNumber";
        public static final String COLUMN_NAME_POINTS_TO_GAIN = "pointsToGain";
        public static final String COLUMN_NAME_POINTS_PER_CLICK = "pointsPerClick";

        static final String SQL_CREATE_QUERY =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                        COLUMN_NAME_LEVEL_NUMBER + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_POINTS_TO_GAIN + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_POINTS_PER_CLICK + INTEGER_TYPE + ")";
    }
}
