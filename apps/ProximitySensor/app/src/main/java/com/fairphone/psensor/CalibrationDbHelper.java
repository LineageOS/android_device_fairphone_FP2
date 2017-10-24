package com.fairphone.psensor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalibrationDbHelper extends SQLiteOpenHelper {

    private static final String DATETIME_TYPE = " DATETIME";
    private static final String INTEGER_TYPE = " INTEGER";

    private static final String DEFAULT = " DEFAULT";
    private static final String CURRENT_TIMESTAMP = " CURRENT_TIMESTAMP";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CalibrationContract.CalibrationData.TABLE_NAME + " (" +
                    CalibrationContract.CalibrationData._ID + " INTEGER PRIMARY KEY," +
                    CalibrationContract.CalibrationData.COLUMN_NAME_TIMESTAMP + DATETIME_TYPE + DEFAULT + CURRENT_TIMESTAMP + COMMA_SEP +
                    CalibrationContract.CalibrationData.COLUMN_NAME_PREVIOUS_NEAR + INTEGER_TYPE + COMMA_SEP +
                    CalibrationContract.CalibrationData.COLUMN_NAME_PREVIOUS_FAR + INTEGER_TYPE + COMMA_SEP +
                    CalibrationContract.CalibrationData.COLUMN_NAME_PREVIOUS_OFFSET + INTEGER_TYPE + COMMA_SEP +
                    CalibrationContract.CalibrationData.COLUMN_NAME_NEAR + INTEGER_TYPE + COMMA_SEP +
                    CalibrationContract.CalibrationData.COLUMN_NAME_FAR + INTEGER_TYPE + COMMA_SEP +
                    CalibrationContract.CalibrationData.COLUMN_NAME_OFFSET + INTEGER_TYPE + COMMA_SEP +
                    CalibrationContract.CalibrationData.COLUMN_NAME_APP_VERSION + INTEGER_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CalibrationContract.CalibrationData.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CalibrationData.db";

    public CalibrationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
