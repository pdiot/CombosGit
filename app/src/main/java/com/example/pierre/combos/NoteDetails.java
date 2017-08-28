package com.example.pierre.combos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteDetails extends Activity {

    protected String noteId;
    protected String note;
    protected String tag;
    protected String character;

    protected EditText tagTextBox;
    protected EditText noteTextBox;

    private final static String TAG_RETAINED_FRAGMENT = "ComboSamplerNoteDetailsFragment";
    private NoteDetailsFragment mRetainedFragment;

    // our Db helper
    private ComboDatabaseHelper myDbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        // We handle our fragment here

        // finding the retained fragment on restarts
        android.app.FragmentManager fm = getFragmentManager();
        mRetainedFragment = (NoteDetailsFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // Create the fragment and data the first time we go through onCreate()
        if (mRetainedFragment == null) {
            // instanciate the fragment
            mRetainedFragment = new NoteDetailsFragment();
            // we add the Fragment to the list of Fragments handled by our FragmentManager
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // we set our initial Data
            noteId = this.getIntent().getStringExtra("ID");
            note = this.getIntent().getStringExtra("Note");
            tag = this.getIntent().getStringExtra("Tag");
            character = this.getIntent().getStringExtra("Character");

            mRetainedFragment.setNoteString(note);
            mRetainedFragment.setIdString(noteId);
            mRetainedFragment.setTagString(tag);
            mRetainedFragment.setCharacterString(character);

        }

        note = mRetainedFragment.getNoteString();
        noteId = mRetainedFragment.getIdString();
        tag = mRetainedFragment.getTagString();
        character = mRetainedFragment.getCharacterString();

        tagTextBox = (EditText)findViewById(R.id.note_tag);
        noteTextBox = (EditText)findViewById(R.id.note_text);

        // TreeObserver : to call a function as soon as the layout it is associated with is drawn

        ViewTreeObserver vto = tagTextBox.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tagTextBox.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tagTextBox.setText(tag);
            }
        });

        ViewTreeObserver vto2 = noteTextBox.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                noteTextBox.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                noteTextBox.setText(note);
            }
        });


        // Open up our Database in case we want to save some stuff

        myDbHelper = new ComboDatabaseHelper(getApplicationContext());

        Log.d("Log Combo Details", "Contenu de ID " + noteId);
        Log.d("Log Combo Details", "Contenu de tag " + tag);
        Log.d("Log Combo Details", "Contenu de note " + note);

    }

    protected void updateNoteDB (View view) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Toast toast = new Toast(getApplicationContext());

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.NotesTables.COLUMN_NAME_TAG, tagTextBox.getText().toString());
        values.put(DatabaseContract.NotesTables.COLUMN_NAME_NOTE, noteTextBox.getText().toString());

        String selection = DatabaseContract.NotesTables._ID + "= ?";
        String[] selectionArgs = {noteId};

        int count = db.update(
                DatabaseContract.NotesTables.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        if (count > 0) {
            toast = toast.makeText(getApplicationContext(), "Note updated", Toast.LENGTH_SHORT);
            toast.show();
            Log.d("Log NoteDetails", "Lines updated : " +count);
        } else {
            toast = toast.makeText(getApplicationContext(), "Error when updating note", Toast.LENGTH_SHORT);
            toast.show();
        }
        db.close();
        Log.d("Log NoteDetails", "Nombre de lignes mises à jour : " + count);

    }

    protected void deleteNoteDB (View view) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Toast toast = new Toast(getApplicationContext());

        String selection = DatabaseContract.NotesTables._ID + " = ?";
        String[] selectionArgs = {noteId};
        int count = db.delete(DatabaseContract.NotesTables.TABLE_NAME, selection, selectionArgs);
        if (count > 0) {
            toast = toast.makeText(getApplicationContext(), "Note successfully deleted", Toast.LENGTH_LONG);
            toast.show();
            Log.d("Log NoteDetails", "Lines deleted : " +count);
            getBack();
        } else {
            toast = toast.makeText(getApplicationContext(), "Error when deleting note", Toast.LENGTH_SHORT);
            toast.show();
        }
        db.close();

    }

    protected void getBack(View view) {
        Intent intent = new Intent(this, MyNotes.class);
        intent.putExtra("Source", "Details");
        intent.putExtra("Character", character);
        startActivity(intent);
    }
    protected void getBack() {
        Intent intent = new Intent(this, MyNotes.class);
        intent.putExtra("Source", "Details");
        intent.putExtra("Character", character);
        startActivity(intent);
    }

    protected void onDestroy () {
        mRetainedFragment.setTagString(tagTextBox.getText().toString());
        mRetainedFragment.setNoteString(noteTextBox.getText().toString());
        super.onDestroy();
    }

    public void ToHome (View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        getBack();
    }
}
