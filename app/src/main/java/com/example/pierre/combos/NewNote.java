package com.example.pierre.combos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewNote extends Activity {

    // our Db helper
    private ComboDatabaseHelper myDbHelper ;
    protected String character;
    protected Button characterButton;
    protected String tagString;
    protected String noteTextString;
    protected Toast toast;
    // Fragments handle the conservation of information if / when onDestroy is called to rebuild the activity (ie on an orientation change for example)
    private final static String TAG_RETAINED_FRAGMENT = "ComboSamplerCharacterNewNoteFragment";
    private NoteDetailsFragment mRetainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        if (getIntent().getExtras() != null){
            Log.d("LogNewNote", "getExtras != null");
            character = getIntent().getStringExtra("Character");
            Log.d("LogNewNote", "Character = " + character);
        }
        else {
            character = "nope";
            Log.d("LogNewNote", "Character = " + character);
        }

        ChangeFont();

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
            String tmp = character;
            mRetainedFragment.setCharacterString(tmp);
            mRetainedFragment.setTagString("");
            mRetainedFragment.setNoteString("");
        }

        character = mRetainedFragment.getCharacterString();
        noteTextString = mRetainedFragment.getNoteString();
        tagString = mRetainedFragment.getTagString();

        // Open up our Database in case we want to save some stuff

        myDbHelper = new ComboDatabaseHelper(getApplicationContext());

        // if we have "loaded" a fragment
        Log.d("LogMyCombos", "Character loaded on activity create : " + character);
        if (character != "nope"){
            fancyBackground();
        }
    }

    protected void ChangeFont () {

        Typeface typefaceButtons =  Typeface.createFromAsset(getAssets(), "fonts/tarrgetacad.ttf");
        LinearLayout layoutV = (LinearLayout) findViewById(R.id.characters_holder);
        LinearLayout layoutH;
        Button button;
        for (int i = 0; i < layoutV.getChildCount(); i++){
            layoutH = (LinearLayout) layoutV.getChildAt(i);
            for (int j = 0; j < layoutH.getChildCount(); j++){
                button = (Button) layoutH.getChildAt(j);
                button.setTypeface(typefaceButtons);
            }
        }
    }

    protected void fancyBackground () {
        LinearLayout layout = (LinearLayout) findViewById(R.id.changethisbackground);
        switch (character) {
            case "Akuma" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_akuma));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Alisa" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_alisa));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Asuka" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_asuka));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Bob" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_bob));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Bryan" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_bryan));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Claudio" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_claudio));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Devil Jin" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_djin));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Dragunov" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_dragunov));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Eddy" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_eddy));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Eliza" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_eliza));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Feng" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_feng));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Gigas" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_gigas));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Heihachi" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_heihachi));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Hwoarang" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_hwaorang));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Jack7" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_jack7));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Jin" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_jin));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Josie" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_josie));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Katarina" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_katarina));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Kazumi" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_kazumi));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Kazuya" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_kaz));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "King" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_king));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Kuma" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_kuma));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Lars" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_lars));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Law" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_law));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Lee" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_lee));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Leo" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_leo));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Lili" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_lili));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Lucky Chloe" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_luckychloe));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Master Raven" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_masterraven));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Miguel" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_miguel));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Nina" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_nina));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Panda" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_panda));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Paul" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_paul));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Shaheen" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_shaheen));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Steve" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_steve));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Xiaoyu" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_ling));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;
            case "Yoshimitsu" :
                layout.setBackground(getResources().getDrawable(R.drawable.bodyshot_yoshimitsu));
                layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackground(getResources().getDrawable(R.drawable.transparent_background));
                break;

            default:  layout = (LinearLayout) findViewById(R.id.notcharacters);
                layout.setBackgroundColor(getResources().getColor(R.color.darkBlue));
        }
    }

    public void ChooseCharacter (View view) {
        Button b = (Button) view;
        characterButton = b;

        LinearLayout layoutV = (LinearLayout) findViewById(R.id.characters_holder);
        for (int i = 0; i < layoutV.getChildCount(); i++){
            LinearLayout layoutH = (LinearLayout) layoutV.getChildAt(i);
            for (int j = 0; j < layoutH.getChildCount(); j ++){
                Button butt = (Button) layoutH.getChildAt(j);
                if (b.equals(butt)){ // show our butt
                    butt.setAlpha(1f);
                }
                else { // hide that ass
                    butt.setAlpha(.7f);
                }
            }
        }

        character = b.getTag().toString();

        fancyBackground();
    }

    public void ResetFields () {
        EditText noteTagText = (EditText) findViewById(R.id.note_tag);
        EditText noteTextText = (EditText) findViewById(R.id.note_text);

        noteTagText.setText("");
        noteTextText.setText("");

    }


    public void SaveNote (View view) {

        EditText noteTagText = (EditText) findViewById(R.id.note_tag);
        EditText noteTextText = (EditText) findViewById(R.id.note_text);

        if (character == "nope") {
            toast = new Toast(getApplicationContext());
            toast = toast.makeText(getApplicationContext(), "Please select a character", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            noteTextString = noteTextText.getText().toString();
            tagString = noteTagText.getText().toString();

            SQLiteDatabase db = myDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.NotesTables.COLUMN_NAME_CHARACTER, character);
            values.put(DatabaseContract.NotesTables.COLUMN_NAME_NOTE, noteTextString);
            values.put(DatabaseContract.NotesTables.COLUMN_NAME_TAG, tagString);

            long newRowId = db.insert(DatabaseContract.NotesTables.TABLE_NAME, null, values);

            if (newRowId > 0) {
                toast = new Toast(getApplicationContext());
                toast = toast.makeText(getApplicationContext(), "Successfully saved note", Toast.LENGTH_SHORT);
                toast.show();
                ResetFields();
            }
            else {
                toast = new Toast(getApplicationContext());
                toast = toast.makeText(getApplicationContext(), "Error when saving note", Toast.LENGTH_LONG);
                toast.show();
            }
            db.close();
        }
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public void ToHome (View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void onDestroy() {
        mRetainedFragment.setNoteString(noteTextString);
        mRetainedFragment.setTagString(tagString);
        mRetainedFragment.setCharacterString(character);

        super.onDestroy();
    }

}
