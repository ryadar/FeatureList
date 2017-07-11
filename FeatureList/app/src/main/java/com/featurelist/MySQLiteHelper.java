package com.featurelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS = "feature_list";
    public static final String COLUMN_ID = "_id";
    public static final String NAME_COLUMN = "name";
    public static final String STATUS_COLUMN = "status";
    public static final String TIME_LEFT_COLUMN = "timeLeft";
    public static final String CATEGORY_COLUMN = "category";

    private static final String DATABASE_NAME = "features.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, "
            + NAME_COLUMN + " text not null," +
            STATUS_COLUMN + " text not null," +
            TIME_LEFT_COLUMN + " text not null," +
            CATEGORY_COLUMN + " text not null" +
            ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(sqLiteDatabase);
    }
}
