package com.example.pierre.combos;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {


    // our Db helper
    ComboDatabaseHelper myDbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);

        Typeface typefaceTitle = Typeface.createFromAsset(getAssets(), "fonts/monoton-regular.ttf");
        titleTextView.setTypeface(typefaceTitle);

        Button myCombosButton = (Button) findViewById(R.id.buttonMyCombos);
        Button newComboButton = (Button) findViewById(R.id.buttonNewCombo);

        Typeface typefaceButtons =  Typeface.createFromAsset(getAssets(), "fonts/tarrgetengrave.ttf");
        myCombosButton.setTypeface(typefaceButtons);
        newComboButton.setTypeface(typefaceButtons);

        // Open up our Database in case we want to save some stuff

        myDbHelper = new ComboDatabaseHelper(getApplicationContext());

        // Set our db up :
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER,
                DatabaseContract.CharactersTable.COLUMN_NAME_ICON
        };

        /* WHERE Clause example
        // Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };
        */

        String sortOrder = DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER + " ASC";

        Cursor cursor = db.query(
                DatabaseContract.CharactersTable.TABLE_NAME,        // Table name
                projection,                                         // Columns to be returned
                null,                                               // Columns for the where clause, see example (would be selection)
                null,                                               // Values for the where clause (would be selectionArgs)
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // sort order  (by ID here)
        );

        List resultsIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER)
            );
            resultsIds.add(itemId);
        }
        cursor.close();

        Log.d("Main, gestion DB", "Names in base before insert: " + resultsIds.toString());

        if (resultsIds.size() != 38) {
            InsertCharactersInDb();
        }



    }

    protected void InsertCharactersInDb() {


        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        long newRowID;
        // values associates a KEY (here our column name) with values (here our strings)
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Akuma");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");

        // this returns the id to which it was inserted
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);

        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Alisa");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Asuka");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Bob");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Bryan");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Claudio");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Devil Jin");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Dragunov");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Eddy");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Eliza");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Feng");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Gigas");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Heihachi");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Hwoarang");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Jack7");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Jin");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Josie");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Katarina");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Kazumi");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Kazuya");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "King");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Kuma");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Lars");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Law");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Lee");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Leo");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Lili");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Lucky Chloe");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Master Raven");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Miguel");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Nina");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Panda");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Paul");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Shaheen");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Steve");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Xiaoyu");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "Yoshimitsu");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);

        // Test char for testing saving combos
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_CHARACTER, "TestCharacter");
        values.put(DatabaseContract.CharactersTable.COLUMN_NAME_ICON, "NYI");
        newRowID = db.insert(DatabaseContract.CharactersTable.TABLE_NAME, null, values);
        Log.d("NewCombo : Database", "Character entry created at ID " + newRowID);

    }



    public void newCombo(View view) {
        Intent intent = new Intent(this, NewCombo.class);
        startActivity(intent);
    }
}
