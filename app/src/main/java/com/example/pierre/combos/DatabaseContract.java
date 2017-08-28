package com.example.pierre.combos;

import android.provider.BaseColumns;

/**
 * Created by pierre on 26/08/2017.
 */

public class DatabaseContract {
    private DatabaseContract(){

    }

    public static class ComboTable implements BaseColumns{
        public static final String TABLE_NAME = "Combos";
        public static final String COLUMN_NAME_CHARACTER = "Character";
        public static final String COLUMN_NAME_INPUTS = "InputList";
        public static final String COLUMN_NAME_DAMAGE = "Damage";
        public static final String COLUMN_NAME_TAG = "ComboTag";
    }

    public static class NotesTables implements BaseColumns {
        public static final String TABLE_NAME = "Notes";
        public static final String COLUMN_NAME_CHARACTER = "Character";
        public static final String COLUMN_NAME_TAG = "Tag";
        public static final String COLUMN_NAME_NOTE = "Note";
    }

}
