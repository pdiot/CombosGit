package com.example.pierre.combos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by pierre on 26/08/2017.
 */



public class ComboDatabaseHelper extends SQLiteOpenHelper{

    private static final String SQL_CREATE_COMBOS_TABLE =
            "CREATE TABLE " + DatabaseContract.ComboTable.TABLE_NAME + " (" +
                    DatabaseContract.ComboTable._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER + " TEXT," +
                    DatabaseContract.ComboTable.COLUMN_NAME_TAG + " TEXT," +
                    DatabaseContract.ComboTable.COLUMN_NAME_INPUTS + " TEXT," +
                    DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE + " INTEGER)"
            ;

    private static final String SQL_CREATE_NOTES_TABLE =
            "CREATE TABLE " + DatabaseContract.NotesTables.TABLE_NAME + " (" +
                    DatabaseContract.NotesTables._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.NotesTables.COLUMN_NAME_CHARACTER + " TEXT," +
                    DatabaseContract.NotesTables.COLUMN_NAME_TAG + " TEXT," +
                    DatabaseContract.NotesTables.COLUMN_NAME_NOTE + " TEXT)"
            ;

    private static final String SQL_DELETE_COMBOS =
            "DROP TABLE IF EXISTS " + DatabaseContract.ComboTable.TABLE_NAME;
    private static final String SQL_DELETE_NOTES =
            "DROP TABLE IF EXISTS " + DatabaseContract.NotesTables.TABLE_NAME;

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "ComboSampler.db";

    public ComboDatabaseHelper (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COMBOS_TABLE);
        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL(SQL_DELETE_COMBOS);
        db.execSQL(SQL_DELETE_NOTES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldV, int newV) {
        onUpgrade(db, oldV, newV);
    }

}
