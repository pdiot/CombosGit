package com.example.pierre.combos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.pierre.combos.R.drawable.b;

public class SavingCombo extends Activity {

    protected String character;
    protected Button characterButton;
    protected Toast toast;
    protected ArrayList<String> comboInput;

    // our Db helper
    ComboDatabaseHelper myDbHelper ;


    // Fragments handle the conservation of information if / when onDestroy is called to rebuild the activity (ie on an orientation change for example)
    private final static String TAG_RETAINED_FRAGMENT = "ComboSamplerCharacterFragment";
    private CharacterPickFragment mRetainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_combo);

        comboInput = this.getIntent().getStringArrayListExtra("input");

        // We handle our fragment here

        // finding the retained fragment on restarts
        android.app.FragmentManager fm = getFragmentManager();
        mRetainedFragment = (CharacterPickFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // Create the fragment and data the first time we go through onCreate()
        if (mRetainedFragment == null) {
            // instanciate the fragment
            mRetainedFragment = new CharacterPickFragment();
            // we add the Fragment to the list of Fragments handled by our FragmentManager
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // we set our initial Data
            String tmp = "nope";
            mRetainedFragment.setData(tmp);
        }

        character = mRetainedFragment.getData();

        // Open up our Database in case we want to save some stuff

        myDbHelper = new ComboDatabaseHelper(getApplicationContext());

        // ChangeFont();
    }

    protected void ChangeFont () {

        Typeface typefaceButtons =  Typeface.createFromAsset(getAssets(), "fonts/tarrget.ttf");
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
    }

    public void SaveCombo (View view) {

        EditText textTag = (EditText) findViewById(R.id.combo_tag);
        EditText textDamage = (EditText) findViewById(R.id.combo_damage);

        if (character == "nope") {
            toast = new Toast(getApplicationContext());
            toast = toast.makeText(getApplicationContext(), "Please select a character", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            SQLiteDatabase db = myDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER,character);
            values.put(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,comboInput.toString());
            values.put(DatabaseContract.ComboTable.COLUMN_NAME_TAG,textTag.getText().toString());
            values.put(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE,textDamage.getText().toString());

            long newRowId = db.insert(DatabaseContract.ComboTable.TABLE_NAME, null, values);

            Log.d("Saving Combo : insertdb", "Insertion du combo à l'ID : " + newRowId + ", tag combo = " + textTag.getText().toString());

            if (newRowId > 0) {

                Log.d("Saving Combo : insertdb", "new row id > 0");
                toast = new Toast(getApplicationContext());
                toast = toast.makeText(getApplicationContext(), "Combo enregistré avec succès, ID n°" + newRowId, Toast.LENGTH_LONG);
                toast.show();
                Log.d("Saving Combo : insertdb", "toast ok");
                db.close();
                NewCombo();

            }
            else {
                Log.d("Saving Combo : insertdb", "new row id < 0");
                toast = new Toast(getApplicationContext());
                toast = toast.makeText(getApplicationContext(), "erreur lors de l'enregistrement du combo", Toast.LENGTH_LONG);
                toast.show();
                Log.d("Saving Combo : insertdb", "toast ok");
                db.close();
            }
        }
    }

    protected void NewCombo(View view) {
        Intent intent = new Intent(this, NewCombo.class);
        startActivity(intent);
    }

    protected void NewCombo() {
        Intent intent = new Intent(this, NewCombo.class);
        startActivity(intent);
    }

    protected void ToHome (View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    protected void ToHome () {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    protected void getBack(View view) {
        Intent intent = new Intent(this, NewCombo.class);
        intent.putExtra("Inputs", comboInput);
        startActivity(intent);
    }

    protected void getBack() {
        Intent intent = new Intent(this, NewCombo.class);
        intent.putExtra("Inputs", comboInput);
        startActivity(intent);
    }

    public void onDestroy(){
        myDbHelper.close();
        mRetainedFragment.setData(character);
        super.onDestroy();
    }

    public void onBackPressed() {
        getBack();
    }
}
