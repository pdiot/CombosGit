package com.example.pierre.combos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCombos extends Activity {

    // our Db helper
    private ComboDatabaseHelper myDbHelper ;
    protected String character;
    protected Button characterButton;
    protected TableRow.LayoutParams tagParams;
    protected TableRow.LayoutParams inputsParams;
    protected TableRow.LayoutParams damageParams;

    // Fragments handle the conservation of information if / when onDestroy is called to rebuild the activity (ie on an orientation change for example)
    private final static String TAG_RETAINED_FRAGMENT = "ComboSamplerCharacterMyCombosFragment";
    private CharacterPickFragment mRetainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_combos);

        if (getIntent().getExtras() != null){
            Log.d("LogMyCombos", "getExtras != null");
            if (getIntent().getStringExtra("Source").equals("Details")){
                character = getIntent().getStringExtra("Character");
                Log.d("LogMyCombos", "Character = " + character);
            }
            else {
                character = "nope";
            }
        }else {
            character = "nope";
        }

        ChangeFont();

        // init our combo diplayer params
        tagParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2);
        inputsParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 4);
        damageParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1);


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
            String tmp = character;
            mRetainedFragment.setData(tmp);
        }

        character = mRetainedFragment.getData();

        // Open up our Database in case we want to save some stuff

        myDbHelper = new ComboDatabaseHelper(getApplicationContext());

        // if we have "loaded" a fragment
        Log.d("LogMyCombos", "Character loaded on activity create : " + character);
        if (character != "nope"){
            LoadCombosForCurrentCharacterSortedTag();
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

        LoadCombosForCurrentCharacterSortedTag();
    }
/*
    public void LoadCombosForCurrentCharacter () {

        View sep;


        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.ComboTable._ID,
                DatabaseContract.ComboTable.COLUMN_NAME_TAG,
                DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,
                DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE
        };

        // WHERE Clause example
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER + " = ?";
        String[] selectionArgs = { character };

        String sortOrder = DatabaseContract.ComboTable._ID + " ASC";

        Cursor cursor = db.query(
                DatabaseContract.ComboTable.TABLE_NAME,        // Table name
                projection,                                         // Columns to be returned
                selection,                                               // Columns for the where clause, see example (would be selection)
                selectionArgs,                                               // Values for the where clause (would be selectionArgs)
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // sort order  (by ID here)
        );

        ArrayList<String[]> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable._ID));
            String tagString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_TAG));
            String inputString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS));
            String damageString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE));
            String[] resultsString = new String [] {id, tagString, inputString, damageString};
            results.add(resultsString);
        }
        cursor.close();


        // We remove previously written down combos

        LinearLayout verticalHolder = (LinearLayout) findViewById(R.id.combosVerticalLayout);

        if (verticalHolder.getChildCount() != 0) { // if verticalHolder has children here, they're from a previous load, we want to delete them
            verticalHolder.removeAllViews();
        }

        for (int i=0; i<results.size(); i++) {  // for each of the results from our db
            String [] combo = results.get(i);
            Log.d("LogMyCombos", "Combo retrieved : " + combo[0] + ", " + combo[1] + ", " + combo[2]);

            LinearLayout comboHolder = new LinearLayout(this);
            comboHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            comboHolder.setOrientation(LinearLayout.HORIZONTAL);

            TextView idView = new TextView(this);
            idView.setText(combo[0]);
            idView.setVisibility(TextView.GONE);
            comboHolder.addView(idView);

            TextView tagView = new TextView(this);
            tagView.setLayoutParams(tagParams);
            tagView.setPadding(5,0,5,0);
            tagView.setText(combo[1]);
            tagView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(tagView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView inputsView = new TextView(this);
            inputsView.setLayoutParams(inputsParams);
            inputsView.setPadding(5,0,5,0);
            inputsView.setText(combo[2]);
            inputsView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(inputsView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView damageView = new TextView(this);
            damageView.setLayoutParams(damageParams);
            damageView.setPadding(5,0,5,0);
            damageView.setText(combo[3]);
            damageView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(damageView);


            verticalHolder.addView(comboHolder);
            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_horizontal_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            verticalHolder.addView(sep);

            // On click listener to view the combo with more details
            comboHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout l = (LinearLayout) v;
                    // each layout has 6 children : id, tag, sep, input, sep, damage
                    TextView id = (TextView) l.getChildAt(0);
                    TextView tag = (TextView) l.getChildAt(1);
                    TextView input = (TextView) l.getChildAt(3);
                    TextView damage = (TextView) l.getChildAt(5);
                    Intent intent = new Intent(getApplicationContext(), ComboDetails.class);
                    intent.putExtra("Input", input.getText());
                    intent.putExtra("Tag", tag.getText());
                    intent.putExtra("Damage", damage.getText());
                    intent.putExtra("ID", id.getText());
                    intent.putExtra("Character", character);
                    Log.d("LogMyCombos", "Extra Input vaut : " + input.getText());
                    Log.d("LogMyCombos", "Extra Tag vaut : " + tag.getText());
                    Log.d("LogMyCombos", "Extra Damage vaut : " + damage.getText());
                    Log.d("LogMyCombos", "Extra id vaut : " + id.getText());

                    startActivity(intent);
                }
            });
        }
    }
*/
    public void LoadCombosForCurrentCharacterSortedTag () {

        View sep;


        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.ComboTable._ID,
                DatabaseContract.ComboTable.COLUMN_NAME_TAG,
                DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,
                DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE
        };

        // WHERE Clause example
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER + " = ?";
        String[] selectionArgs = { character };

        String sortOrder = DatabaseContract.ComboTable.COLUMN_NAME_TAG + " ASC";

        Cursor cursor = db.query(
                DatabaseContract.ComboTable.TABLE_NAME,        // Table name
                projection,                                         // Columns to be returned
                selection,                                               // Columns for the where clause, see example (would be selection)
                selectionArgs,                                               // Values for the where clause (would be selectionArgs)
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // sort order  (by ID here)
        );

        ArrayList<String[]> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable._ID));
            String tagString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_TAG));
            String inputString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS));
            String damageString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE));
            String[] resultsString = new String [] {id, tagString, inputString, damageString};
            results.add(resultsString);
        }
        cursor.close();


        // We remove previously written down combos

        LinearLayout verticalHolder = (LinearLayout) findViewById(R.id.combosVerticalLayout);

        if (verticalHolder.getChildCount() != 0) { // if verticalHolder has children here, they're from a previous load, we want to delete them
            verticalHolder.removeAllViews();
        }

        for (int i=0; i<results.size(); i++) {  // for each of the results from our db
            String [] combo = results.get(i);
            Log.d("LogMyCombos", "Combo retrieved : " + combo[0] + ", " + combo[1] + ", " + combo[2]);

            LinearLayout comboHolder = new LinearLayout(this);
            comboHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            comboHolder.setOrientation(LinearLayout.HORIZONTAL);

            TextView idView = new TextView(this);
            idView.setText(combo[0]);
            idView.setVisibility(TextView.GONE);
            comboHolder.addView(idView);

            TextView tagView = new TextView(this);
            tagView.setLayoutParams(tagParams);
            tagView.setPadding(5,0,5,0);
            tagView.setText(combo[1]);
            tagView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(tagView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView inputsView = new TextView(this);
            inputsView.setLayoutParams(inputsParams);
            inputsView.setPadding(5,0,5,0);
            inputsView.setText(combo[2]);
            inputsView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(inputsView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView damageView = new TextView(this);
            damageView.setLayoutParams(damageParams);
            damageView.setPadding(5,0,5,0);
            damageView.setText(combo[3]);
            damageView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(damageView);


            verticalHolder.addView(comboHolder);
            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_horizontal_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            verticalHolder.addView(sep);

            // On click listener to view the combo with more details
            comboHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout l = (LinearLayout) v;
                    // each layout has 6 children : id, tag, sep, input, sep, damage
                    TextView id = (TextView) l.getChildAt(0);
                    TextView tag = (TextView) l.getChildAt(1);
                    TextView input = (TextView) l.getChildAt(3);
                    TextView damage = (TextView) l.getChildAt(5);
                    Intent intent = new Intent(getApplicationContext(), ComboDetails.class);
                    intent.putExtra("Input", input.getText());
                    intent.putExtra("Tag", tag.getText());
                    intent.putExtra("Damage", damage.getText());
                    intent.putExtra("ID", id.getText());
                    intent.putExtra("Character", character);
                    Log.d("LogMyCombos", "Extra Input vaut : " + input.getText());
                    Log.d("LogMyCombos", "Extra Tag vaut : " + tag.getText());
                    Log.d("LogMyCombos", "Extra Damage vaut : " + damage.getText());
                    Log.d("LogMyCombos", "Extra id vaut : " + id.getText());

                    startActivity(intent);
                }
            });
        }

    }

    public void LoadCombosForCurrentCharacterSortedDamage () {

        View sep;


        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.ComboTable._ID,
                DatabaseContract.ComboTable.COLUMN_NAME_TAG,
                DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,
                DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE
        };

        // WHERE Clause example
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER + " = ?";
        String[] selectionArgs = { character };

        String sortOrder = DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE + " DESC";

        Cursor cursor = db.query(
                DatabaseContract.ComboTable.TABLE_NAME,        // Table name
                projection,                                         // Columns to be returned
                selection,                                               // Columns for the where clause, see example (would be selection)
                selectionArgs,                                               // Values for the where clause (would be selectionArgs)
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // sort order  (by ID here)
        );

        ArrayList<String[]> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable._ID));
            String tagString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_TAG));
            String inputString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS));
            String damageString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE));
            String[] resultsString = new String [] {id, tagString, inputString, damageString};
            results.add(resultsString);
        }
        cursor.close();


        // We remove previously written down combos

        LinearLayout verticalHolder = (LinearLayout) findViewById(R.id.combosVerticalLayout);

        if (verticalHolder.getChildCount() != 0) { // if verticalHolder has children here, they're from a previous load, we want to delete them
            verticalHolder.removeAllViews();
        }

        for (int i=0; i<results.size(); i++) {  // for each of the results from our db
            String [] combo = results.get(i);
            Log.d("LogMyCombos", "Combo retrieved : " + combo[0] + ", " + combo[1] + ", " + combo[2]);

            LinearLayout comboHolder = new LinearLayout(this);
            comboHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            comboHolder.setOrientation(LinearLayout.HORIZONTAL);

            TextView idView = new TextView(this);
            idView.setText(combo[0]);
            idView.setVisibility(TextView.GONE);
            comboHolder.addView(idView);

            TextView tagView = new TextView(this);
            tagView.setLayoutParams(tagParams);
            tagView.setPadding(5,0,5,0);
            tagView.setText(combo[1]);
            tagView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(tagView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView inputsView = new TextView(this);
            inputsView.setLayoutParams(inputsParams);
            inputsView.setPadding(5,0,5,0);
            inputsView.setText(combo[2]);
            inputsView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(inputsView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView damageView = new TextView(this);
            damageView.setLayoutParams(damageParams);
            damageView.setPadding(5,0,5,0);
            damageView.setText(combo[3]);
            damageView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(damageView);


            verticalHolder.addView(comboHolder);
            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_horizontal_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            verticalHolder.addView(sep);

            // On click listener to view the combo with more details
            comboHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout l = (LinearLayout) v;
                    // each layout has 6 children : id, tag, sep, input, sep, damage
                    TextView id = (TextView) l.getChildAt(0);
                    TextView tag = (TextView) l.getChildAt(1);
                    TextView input = (TextView) l.getChildAt(3);
                    TextView damage = (TextView) l.getChildAt(5);
                    Intent intent = new Intent(getApplicationContext(), ComboDetails.class);
                    intent.putExtra("Input", input.getText());
                    intent.putExtra("Tag", tag.getText());
                    intent.putExtra("Damage", damage.getText());
                    intent.putExtra("ID", id.getText());
                    intent.putExtra("Character", character);
                    Log.d("LogMyCombos", "Extra Input vaut : " + input.getText());
                    Log.d("LogMyCombos", "Extra Tag vaut : " + tag.getText());
                    Log.d("LogMyCombos", "Extra Damage vaut : " + damage.getText());
                    Log.d("LogMyCombos", "Extra id vaut : " + id.getText());

                    startActivity(intent);
                }
            });
        }

    }

    public void LoadCombosForCurrentCharacterSortedTag (View viewIn) {

        View sep;


        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.ComboTable._ID,
                DatabaseContract.ComboTable.COLUMN_NAME_TAG,
                DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,
                DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE
        };

        // WHERE Clause example
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER + " = ?";
        String[] selectionArgs = { character };

        String sortOrder = DatabaseContract.ComboTable.COLUMN_NAME_TAG + " ASC";

        Cursor cursor = db.query(
                DatabaseContract.ComboTable.TABLE_NAME,        // Table name
                projection,                                         // Columns to be returned
                selection,                                               // Columns for the where clause, see example (would be selection)
                selectionArgs,                                               // Values for the where clause (would be selectionArgs)
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // sort order  (by ID here)
        );

        ArrayList<String[]> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable._ID));
            String tagString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_TAG));
            String inputString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS));
            String damageString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE));
            String[] resultsString = new String [] {id, tagString, inputString, damageString};
            results.add(resultsString);
        }
        cursor.close();


        // We remove previously written down combos

        LinearLayout verticalHolder = (LinearLayout) findViewById(R.id.combosVerticalLayout);

        if (verticalHolder.getChildCount() != 0) { // if verticalHolder has children here, they're from a previous load, we want to delete them
            verticalHolder.removeAllViews();
        }

        for (int i=0; i<results.size(); i++) {  // for each of the results from our db
            String [] combo = results.get(i);
            Log.d("LogMyCombos", "Combo retrieved : " + combo[0] + ", " + combo[1] + ", " + combo[2]);

            LinearLayout comboHolder = new LinearLayout(this);
            comboHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            comboHolder.setOrientation(LinearLayout.HORIZONTAL);

            TextView idView = new TextView(this);
            idView.setText(combo[0]);
            idView.setVisibility(TextView.GONE);
            comboHolder.addView(idView);

            TextView tagView = new TextView(this);
            tagView.setLayoutParams(tagParams);
            tagView.setPadding(5,0,5,0);
            tagView.setText(combo[1]);
            tagView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(tagView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView inputsView = new TextView(this);
            inputsView.setLayoutParams(inputsParams);
            inputsView.setPadding(5,0,5,0);
            inputsView.setText(combo[2]);
            inputsView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(inputsView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView damageView = new TextView(this);
            damageView.setLayoutParams(damageParams);
            damageView.setPadding(5,0,5,0);
            damageView.setText(combo[3]);
            damageView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(damageView);


            verticalHolder.addView(comboHolder);
            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_horizontal_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            verticalHolder.addView(sep);

            // On click listener to view the combo with more details
            comboHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout l = (LinearLayout) v;
                    // each layout has 6 children : id, tag, sep, input, sep, damage
                    TextView id = (TextView) l.getChildAt(0);
                    TextView tag = (TextView) l.getChildAt(1);
                    TextView input = (TextView) l.getChildAt(3);
                    TextView damage = (TextView) l.getChildAt(5);
                    Intent intent = new Intent(getApplicationContext(), ComboDetails.class);
                    intent.putExtra("Input", input.getText());
                    intent.putExtra("Tag", tag.getText());
                    intent.putExtra("Damage", damage.getText());
                    intent.putExtra("ID", id.getText());
                    intent.putExtra("Character", character);
                    Log.d("LogMyCombos", "Extra Input vaut : " + input.getText());
                    Log.d("LogMyCombos", "Extra Tag vaut : " + tag.getText());
                    Log.d("LogMyCombos", "Extra Damage vaut : " + damage.getText());
                    Log.d("LogMyCombos", "Extra id vaut : " + id.getText());

                    startActivity(intent);
                }
            });
        }

    }

    public void LoadCombosForCurrentCharacterSortedDamage (View viewIn) {

        View sep;


        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.ComboTable._ID,
                DatabaseContract.ComboTable.COLUMN_NAME_TAG,
                DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,
                DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE
        };

        // WHERE Clause example
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER + " = ?";
        String[] selectionArgs = { character };

        String sortOrder = DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE + " DESC";

        Cursor cursor = db.query(
                DatabaseContract.ComboTable.TABLE_NAME,        // Table name
                projection,                                         // Columns to be returned
                selection,                                               // Columns for the where clause, see example (would be selection)
                selectionArgs,                                               // Values for the where clause (would be selectionArgs)
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // sort order  (by ID here)
        );

        ArrayList<String[]> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable._ID));
            String tagString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_TAG));
            String inputString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS));
            String damageString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE));
            String[] resultsString = new String [] {id, tagString, inputString, damageString};
            results.add(resultsString);
        }
        cursor.close();


        // We remove previously written down combos

        LinearLayout verticalHolder = (LinearLayout) findViewById(R.id.combosVerticalLayout);

        if (verticalHolder.getChildCount() != 0) { // if verticalHolder has children here, they're from a previous load, we want to delete them
            verticalHolder.removeAllViews();
        }

        for (int i=0; i<results.size(); i++) {  // for each of the results from our db
            String [] combo = results.get(i);
            Log.d("LogMyCombos", "Combo retrieved : " + combo[0] + ", " + combo[1] + ", " + combo[2]);

            LinearLayout comboHolder = new LinearLayout(this);
            comboHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            comboHolder.setOrientation(LinearLayout.HORIZONTAL);

            TextView idView = new TextView(this);
            idView.setText(combo[0]);
            idView.setVisibility(TextView.GONE);
            comboHolder.addView(idView);

            TextView tagView = new TextView(this);
            tagView.setLayoutParams(tagParams);
            tagView.setPadding(5,0,5,0);
            tagView.setText(combo[1]);
            tagView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(tagView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView inputsView = new TextView(this);
            inputsView.setLayoutParams(inputsParams);
            inputsView.setPadding(5,0,5,0);
            inputsView.setText(combo[2]);
            inputsView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(inputsView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView damageView = new TextView(this);
            damageView.setLayoutParams(damageParams);
            damageView.setPadding(5,0,5,0);
            damageView.setText(combo[3]);
            damageView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(damageView);


            verticalHolder.addView(comboHolder);
            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_horizontal_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            verticalHolder.addView(sep);

            // On click listener to view the combo with more details
            comboHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout l = (LinearLayout) v;
                    // each layout has 6 children : id, tag, sep, input, sep, damage
                    TextView id = (TextView) l.getChildAt(0);
                    TextView tag = (TextView) l.getChildAt(1);
                    TextView input = (TextView) l.getChildAt(3);
                    TextView damage = (TextView) l.getChildAt(5);
                    Intent intent = new Intent(getApplicationContext(), ComboDetails.class);
                    intent.putExtra("Input", input.getText());
                    intent.putExtra("Tag", tag.getText());
                    intent.putExtra("Damage", damage.getText());
                    intent.putExtra("ID", id.getText());
                    intent.putExtra("Character", character);
                    Log.d("LogMyCombos", "Extra Input vaut : " + input.getText());
                    Log.d("LogMyCombos", "Extra Tag vaut : " + tag.getText());
                    Log.d("LogMyCombos", "Extra Damage vaut : " + damage.getText());
                    Log.d("LogMyCombos", "Extra id vaut : " + id.getText());

                    startActivity(intent);
                }
            });
        }

    }

    public void LoadCombosForCurrentCharacterSortedInput () {

        View sep;


        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.ComboTable._ID,
                DatabaseContract.ComboTable.COLUMN_NAME_TAG,
                DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,
                DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE
        };

        // WHERE Clause example
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER + " = ?";
        String[] selectionArgs = { character };

        String sortOrder = DatabaseContract.ComboTable.COLUMN_NAME_INPUTS + " ASC";

        Cursor cursor = db.query(
                DatabaseContract.ComboTable.TABLE_NAME,        // Table name
                projection,                                         // Columns to be returned
                selection,                                               // Columns for the where clause, see example (would be selection)
                selectionArgs,                                               // Values for the where clause (would be selectionArgs)
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // sort order  (by ID here)
        );

        ArrayList<String[]> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable._ID));
            String tagString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_TAG));
            String inputString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS));
            String damageString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE));
            String[] resultsString = new String [] {id, tagString, inputString, damageString};
            results.add(resultsString);
        }
        cursor.close();


        // We remove previously written down combos

        LinearLayout verticalHolder = (LinearLayout) findViewById(R.id.combosVerticalLayout);

        if (verticalHolder.getChildCount() != 0) { // if verticalHolder has children here, they're from a previous load, we want to delete them
            verticalHolder.removeAllViews();
        }

        for (int i=0; i<results.size(); i++) {  // for each of the results from our db
            String [] combo = results.get(i);
            Log.d("LogMyCombos", "Combo retrieved : " + combo[0] + ", " + combo[1] + ", " + combo[2]);

            LinearLayout comboHolder = new LinearLayout(this);
            comboHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            comboHolder.setOrientation(LinearLayout.HORIZONTAL);

            TextView idView = new TextView(this);
            idView.setText(combo[0]);
            idView.setVisibility(TextView.GONE);
            comboHolder.addView(idView);

            TextView tagView = new TextView(this);
            tagView.setLayoutParams(tagParams);
            tagView.setPadding(5,0,5,0);
            tagView.setText(combo[1]);
            tagView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(tagView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView inputsView = new TextView(this);
            inputsView.setLayoutParams(inputsParams);
            inputsView.setPadding(5,0,5,0);
            inputsView.setText(combo[2]);
            inputsView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(inputsView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView damageView = new TextView(this);
            damageView.setLayoutParams(damageParams);
            damageView.setPadding(5,0,5,0);
            damageView.setText(combo[3]);
            damageView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(damageView);


            verticalHolder.addView(comboHolder);
            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_horizontal_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            verticalHolder.addView(sep);

            // On click listener to view the combo with more details
            comboHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout l = (LinearLayout) v;
                    // each layout has 6 children : id, tag, sep, input, sep, damage
                    TextView id = (TextView) l.getChildAt(0);
                    TextView tag = (TextView) l.getChildAt(1);
                    TextView input = (TextView) l.getChildAt(3);
                    TextView damage = (TextView) l.getChildAt(5);
                    Intent intent = new Intent(getApplicationContext(), ComboDetails.class);
                    intent.putExtra("Input", input.getText());
                    intent.putExtra("Tag", tag.getText());
                    intent.putExtra("Damage", damage.getText());
                    intent.putExtra("ID", id.getText());
                    intent.putExtra("Character", character);
                    Log.d("LogMyCombos", "Extra Input vaut : " + input.getText());
                    Log.d("LogMyCombos", "Extra Tag vaut : " + tag.getText());
                    Log.d("LogMyCombos", "Extra Damage vaut : " + damage.getText());
                    Log.d("LogMyCombos", "Extra id vaut : " + id.getText());

                    startActivity(intent);
                }
            });
        }

    }

    public void LoadCombosForCurrentCharacterSortedInput (View viewIn) {

        View sep;


        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.ComboTable._ID,
                DatabaseContract.ComboTable.COLUMN_NAME_TAG,
                DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,
                DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE
        };

        // WHERE Clause example
        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER + " = ?";
        String[] selectionArgs = { character };

        String sortOrder = DatabaseContract.ComboTable.COLUMN_NAME_INPUTS + " ASC";

        Cursor cursor = db.query(
                DatabaseContract.ComboTable.TABLE_NAME,        // Table name
                projection,                                         // Columns to be returned
                selection,                                               // Columns for the where clause, see example (would be selection)
                selectionArgs,                                               // Values for the where clause (would be selectionArgs)
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                sortOrder                                           // sort order  (by ID here)
        );

        ArrayList<String[]> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable._ID));
            String tagString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_TAG));
            String inputString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS));
            String damageString = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE));
            String[] resultsString = new String [] {id, tagString, inputString, damageString};
            results.add(resultsString);
        }
        cursor.close();


        // We remove previously written down combos

        LinearLayout verticalHolder = (LinearLayout) findViewById(R.id.combosVerticalLayout);

        if (verticalHolder.getChildCount() != 0) { // if verticalHolder has children here, they're from a previous load, we want to delete them
            verticalHolder.removeAllViews();
        }

        for (int i=0; i<results.size(); i++) {  // for each of the results from our db
            String [] combo = results.get(i);
            Log.d("LogMyCombos", "Combo retrieved : " + combo[0] + ", " + combo[1] + ", " + combo[2]);

            LinearLayout comboHolder = new LinearLayout(this);
            comboHolder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            comboHolder.setOrientation(LinearLayout.HORIZONTAL);

            TextView idView = new TextView(this);
            idView.setText(combo[0]);
            idView.setVisibility(TextView.GONE);
            comboHolder.addView(idView);

            TextView tagView = new TextView(this);
            tagView.setLayoutParams(tagParams);
            tagView.setPadding(5,0,5,0);
            tagView.setText(combo[1]);
            tagView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(tagView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView inputsView = new TextView(this);
            inputsView.setLayoutParams(inputsParams);
            inputsView.setPadding(5,0,5,0);
            inputsView.setText(combo[2]);
            inputsView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(inputsView);

            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_vertical_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            setMargins(sep, 0, 2, 0, 2);
            comboHolder.addView(sep);

            TextView damageView = new TextView(this);
            damageView.setLayoutParams(damageParams);
            damageView.setPadding(5,0,5,0);
            damageView.setText(combo[3]);
            damageView.setTextColor(getResources().getColor(R.color.white));
            comboHolder.addView(damageView);


            verticalHolder.addView(comboHolder);
            sep = new View(this);
            sep.setLayoutParams(findViewById(R.id.sample_horizontal_separator).getLayoutParams());
            sep.setBackgroundColor(getResources().getColor(R.color.paleBlue));
            verticalHolder.addView(sep);

            // On click listener to view the combo with more details
            comboHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout l = (LinearLayout) v;
                    // each layout has 6 children : id, tag, sep, input, sep, damage
                    TextView id = (TextView) l.getChildAt(0);
                    TextView tag = (TextView) l.getChildAt(1);
                    TextView input = (TextView) l.getChildAt(3);
                    TextView damage = (TextView) l.getChildAt(5);
                    Intent intent = new Intent(getApplicationContext(), ComboDetails.class);
                    intent.putExtra("Input", input.getText());
                    intent.putExtra("Tag", tag.getText());
                    intent.putExtra("Damage", damage.getText());
                    intent.putExtra("ID", id.getText());
                    intent.putExtra("Character", character);
                    Log.d("LogMyCombos", "Extra Input vaut : " + input.getText());
                    Log.d("LogMyCombos", "Extra Tag vaut : " + tag.getText());
                    Log.d("LogMyCombos", "Extra Damage vaut : " + damage.getText());
                    Log.d("LogMyCombos", "Extra id vaut : " + id.getText());

                    startActivity(intent);
                }
            });
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

    public void NewCombo(View view) {
        Intent intent = new Intent(this, NewCombo.class);
        startActivity(intent);
    }
}
