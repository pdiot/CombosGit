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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ComboDetails extends Activity {

    protected String comboId;
    protected String inputs;
    protected String tag;
    protected String damage;
    protected ArrayList<String> inputsArray;
    protected String character;

    protected EditText tagTextBox;
    protected EditText damageTextBox;
    protected LinearLayout currentLayout;
    protected int inputsDrawn;
    protected int pictureMaxSize;
    protected int inputsPerLayout;
    protected int drawingHeight;
    protected int drawingWidth;

    private final static String TAG_RETAINED_FRAGMENT = "ComboSamplerComboDetailsFragment";
    private ComboDetailsFragment mRetainedFragment;


    // our Db helper
    private ComboDatabaseHelper myDbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_details);

        // We handle our fragment here

        // finding the retained fragment on restarts
        android.app.FragmentManager fm = getFragmentManager();
        mRetainedFragment = (ComboDetailsFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // Create the fragment and data the first time we go through onCreate()
        if (mRetainedFragment == null) {
            // instanciate the fragment
            mRetainedFragment = new ComboDetailsFragment();
            // we add the Fragment to the list of Fragments handled by our FragmentManager
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // we set our initial Data
            comboId = this.getIntent().getStringExtra("ID");
            inputs = this.getIntent().getStringExtra("Input");
            tag = this.getIntent().getStringExtra("Tag");
            damage = this.getIntent().getStringExtra("Damage");
            character = this.getIntent().getStringExtra("Character");
            inputsArray = new ArrayList<String>();
            parseInput();

            mRetainedFragment.setInputArray(inputsArray);
            mRetainedFragment.setDamageString(damage);
            mRetainedFragment.setIdString(comboId);
            mRetainedFragment.setTagString(tag);
            mRetainedFragment.setCharacterString(character);

        }

        inputsArray = mRetainedFragment.getInputArray();
        damage = mRetainedFragment.getDamageString();
        comboId = mRetainedFragment.getIdString();
        tag = mRetainedFragment.getTagString();
        character = mRetainedFragment.getCharacterString();

        inputsDrawn = 0;
        currentLayout = (LinearLayout)findViewById(R.id.Drawing1); // we'll draw on Drawing1 first
        tagTextBox = (EditText)findViewById(R.id.combo_tag);
        damageTextBox = (EditText)findViewById(R.id.combo_damage);

        // TreeObserver : to call a function as soon as the layout it is associated with is drawn
        ViewTreeObserver vto = currentLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                currentLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                drawingHeight = currentLayout.getHeight();
                drawingWidth = currentLayout.getWidth();

                if (inputsArray.size() != 0) {
                    drawCombo();
                }
            }
        });

        ViewTreeObserver vto2 = tagTextBox.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tagTextBox.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tagTextBox.setText(tag);
            }
        });

        ViewTreeObserver vto3 = currentLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                damageTextBox.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                damageTextBox.setText(damage);
            }
        });


        // Open up our Database in case we want to save some stuff

        myDbHelper = new ComboDatabaseHelper(getApplicationContext());

        Log.d("Log Combo Details", "Contenu de ID " + comboId);
        Log.d("Log Combo Details", "Contenu de tag " + tag);
        Log.d("Log Combo Details", "Contenu de damage " + damage);
        Log.d("Log Combo Details", "Contenu de inputsArray " + inputsArray.toString());

    }

    protected void parseInput (){
        String tmp = inputs;
        String[] tmpArr = tmp.split("\\[|\\, |\\]");
        for (int i = 1; i < tmpArr.length; i++) {
            Log.d("Parsing inputs", "valeur de i : " + i);
            Log.d("Parsing inputs", "Contenu de tmpArr[i] : " + tmpArr[i]);
            String tmp2 = tmpArr[i];
            inputsArray.add(tmp2);
        }
    }

    protected void drawCombo() {
        Log.d("NewCombo", "drawComboInput Called, contenu de ComboInput : " + inputsArray.toString());
        processInputSizes(); // we call it once when drawing a pre-existing combo
        for (int i=0; i<inputsArray.size();i++){
            String input = inputsArray.get(i);
            addVisual(input);
        }
    }

    protected void processInputSizes () {
        /* This function does two things : sets the size of our inputs visuals
                                            sets the number of inputs we will display per line

                                            All depending on the availaible space on the device.
                                            We're limiting ourselves to 5 rows of drawings for lisibility purposes

            It will be called on first input added to the layout


            Pictures max size : drawing layouts min height x drawing layouts min height
            This gives us the number of inputs we can have per drawing layout (currentLayout.getWidth / picture.width).         */

        //DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //float logicalDensity = metrics.density;

        //int pixelLayoutWidth = (int) Math.ceil(currentLayout.getWidth() * logicalDensity);
        //int pixelLayoutHeight = (int) Math.ceil(currentLayout.getHeight() * logicalDensity / 5);
        //toast = Toast.makeText(getApplicationContext(), "Taille de la zone de dessin en pixels : " + pixelLayoutWidth + " x " + pixelLayoutHeight, Toast.LENGTH_LONG);
        //toast.show();

        pictureMaxSize = Math.min(drawingWidth / 10, drawingHeight / 5);
        inputsPerLayout = drawingWidth / pictureMaxSize;
    }

    protected void addVisual(String input) {

        //toast = Toast.makeText(getApplicationContext(), "inputs drawn  début addVisual : " + inputsDrawn, Toast.LENGTH_SHORT);
        //toast.show();

        if (inputsDrawn == inputsPerLayout) {
            switchDrawingLayout(2);
        } else if (inputsDrawn == 2*inputsPerLayout) {
            switchDrawingLayout(3);
        } else if (inputsDrawn == 3*inputsPerLayout) {
            switchDrawingLayout(4);
        } else if (inputsDrawn == 4*inputsPerLayout) {
            switchDrawingLayout(5);
        }

        ImageView newInput = new ImageView(getApplicationContext());
        Drawable inputPicture = getVisual(input);
        newInput.setImageDrawable(inputPicture);
        currentLayout.addView(newInput);
        newInput.getLayoutParams().width = pictureMaxSize;
        newInput.getLayoutParams().height = pictureMaxSize;
        inputsDrawn ++;

        //toast = Toast.makeText(getApplicationContext(), "inputs drawn  fin addVisual : " + inputsDrawn, Toast.LENGTH_SHORT);
        //toast.show();
    }

    protected void switchDrawingLayout(Integer nb){
        switch (nb) {
            case 1 : currentLayout = (LinearLayout)findViewById(R.id.Drawing1);
                currentLayout.setVisibility(LinearLayout.VISIBLE);
            case 2 : currentLayout = (LinearLayout)findViewById(R.id.Drawing2);
                currentLayout.setVisibility(LinearLayout.VISIBLE);
                break;
            case 3 : currentLayout = (LinearLayout)findViewById(R.id.Drawing3);
                currentLayout.setVisibility(LinearLayout.VISIBLE);
                break;

            case 4 : currentLayout = (LinearLayout)findViewById(R.id.Drawing4);
                currentLayout.setVisibility(LinearLayout.VISIBLE);
                break;

            case 5 : currentLayout = (LinearLayout)findViewById(R.id.Drawing5);
                currentLayout.setVisibility(LinearLayout.VISIBLE);
                break;
        }
    }

    protected Drawable getVisual (String str) {
        switch (str) {
            case "ub" : return getResources().getDrawable(R.drawable.ub);
            case "u" : return getResources().getDrawable(R.drawable.u);
            case "uf" : return getResources().getDrawable(R.drawable.uf);
            case "b" : return getResources().getDrawable(R.drawable.b);
            case "n" : return getResources().getDrawable(R.drawable.n);
            case "f" : return getResources().getDrawable(R.drawable.f);
            case "db" : return getResources().getDrawable(R.drawable.db);
            case "d" : return getResources().getDrawable(R.drawable.d);
            case "df" : return getResources().getDrawable(R.drawable.df);
            case "UB" : return getResources().getDrawable(R.drawable.holdub);
            case "U" : return getResources().getDrawable(R.drawable.holdu);
            case "UF" : return getResources().getDrawable(R.drawable.holduf);
            case "B" : return getResources().getDrawable(R.drawable.holdb);
            case "F" : return getResources().getDrawable(R.drawable.holdf);
            case "DB" : return getResources().getDrawable(R.drawable.holddb);
            case "D" : return getResources().getDrawable(R.drawable.holdd);
            case "DF" : return getResources().getDrawable(R.drawable.holddf);
            case "1" : return getResources().getDrawable(R.drawable.draw1);
            case "2" : return getResources().getDrawable(R.drawable.draw2);
            case "3" : return getResources().getDrawable(R.drawable.draw3);
            case "4" : return getResources().getDrawable(R.drawable.draw4);
            case "1+2" : return getResources().getDrawable(R.drawable.draw12);
            case "1+3" : return getResources().getDrawable(R.drawable.draw13);
            case "1+4" : return getResources().getDrawable(R.drawable.draw14);
            case "2+3" : return getResources().getDrawable(R.drawable.draw23);
            case "2+4" : return getResources().getDrawable(R.drawable.draw24);
            case "3+4" : return getResources().getDrawable(R.drawable.draw34);
            case "1+2+3" : return getResources().getDrawable(R.drawable.draw123);
            case "1+2+4" : return getResources().getDrawable(R.drawable.draw124);
            case "2+3+4" : return getResources().getDrawable(R.drawable.draw234);
            case "1+3+4" : return getResources().getDrawable(R.drawable.draw134);
            case "1+2+3+4" : return getResources().getDrawable(R.drawable.draw1234);
            case "RA" : return getResources().getDrawable(R.drawable.drawdefault);
            case "Into" : return getResources().getDrawable(R.drawable.into);
            case "WS" : return getResources().getDrawable(R.drawable.ws);
            default: return getResources().getDrawable(R.drawable.drawdefault);
        }
    }

    protected void updateComboDB (View view) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Toast toast = new Toast(getApplicationContext());

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ComboTable.COLUMN_NAME_TAG, tagTextBox.getText().toString());
        values.put(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE, damageTextBox.getText().toString());

        String selection = DatabaseContract.ComboTable._ID + "= ?";
        String[] selectionArgs = {comboId};

        int count = db.update(
                DatabaseContract.ComboTable.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            toast = toast.makeText(getApplicationContext(), "Combo tag / damage updated", Toast.LENGTH_SHORT);
            toast.show();
            Log.d("Log NoteDetails", "Lines updated : " +count);
        } else {
            toast = toast.makeText(getApplicationContext(), "Error when updating combo", Toast.LENGTH_SHORT);
            toast.show();
        }

        Log.d("Log ComboDetails", "Nombre de lignes mises à jour : " + count);
        db.close();

    }

    protected void deleteComboDB (View view) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        Toast toast = new Toast(getApplicationContext());

        String selection = DatabaseContract.ComboTable._ID + " = ?";
        String[] selectionArgs = {comboId};
        int count = db.delete(DatabaseContract.ComboTable.TABLE_NAME, selection, selectionArgs);


        if (count > 0) {
            toast = toast.makeText(getApplicationContext(), "Combo successfully deleted", Toast.LENGTH_SHORT);
            toast.show();
            Log.d("Log ComboDetails", "Lines deleted : " +count);
            deactivateUpdate();
        } else {
            toast = toast.makeText(getApplicationContext(), "Error when deleting combo", Toast.LENGTH_SHORT);
            toast.show();
        }

        db.close();
        Log.d("Log ComboDetails", "Lines deleted : " +count);
    }

    protected void deactivateUpdate(){
        Button b = (Button) findViewById(R.id.updateBttn);
        b.setVisibility(View.GONE);
        EditText e = (EditText) findViewById(R.id.combo_tag);
        e.setClickable(false);
        e.setFocusable(false);
        e.setText("Combo has been deleted from DB");
        e = (EditText) findViewById(R.id.combo_damage);
        e.setClickable(false);
        e.setFocusable(false);
        e.setText("You can still edit it !");
    }

    protected void getBack(View view) {
        Intent intent = new Intent(this, MyCombos.class);
        intent.putExtra("Source", "Details");
        intent.putExtra("Character", character);
        startActivity(intent);
    }
    protected void getBack() {
        Intent intent = new Intent(this, MyCombos.class);
        intent.putExtra("Source", "Details");
        intent.putExtra("Character", character);
        startActivity(intent);
    }

    protected void onDestroy () {
        mRetainedFragment.setTagString(tagTextBox.getText().toString());
        mRetainedFragment.setDamageString(damageTextBox.getText().toString());
        super.onDestroy();
    }

    public void Edit(View view) {
        Intent intent = new Intent(this, NewCombo.class);
        intent.putExtra("Inputs", inputsArray);
        startActivity(intent);
    }

    public void ToHome (View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        getBack();
    }
}
