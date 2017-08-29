package com.example.pierre.combos;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;

import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;

import static android.R.attr.button;
import static android.R.attr.layout;
import static android.R.attr.seekBarStyle;
import static android.R.attr.visible;

/**
 * Created by pierre on 25/08/2017.
 */

public class NewCombo extends Activity {

    protected ArrayList <String> comboInput;
    protected Toast toast;
    protected LinearLayout currentLayout;
    protected int inputsDrawn;
    protected int pictureMaxSize;
    protected int inputsPerLayout;

    // Fragments handle the conservation of information if / when onDestroy is called to rebuild the activity (ie on an orientation change for example)
    private final static String TAG_RETAINED_FRAGMENT = "ComboSamplerFragment";
    private ComboInputFragment mRetainedFragment;

    protected int drawingHeight;
    protected int drawingWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcombo);

        // We setup the longClickListeners for holding directional inputs
        setLongClickListeners();

        if (getIntent().getExtras() != null) {
            comboInput = getIntent().getStringArrayListExtra("Inputs");
        } else {
            comboInput = new ArrayList<String>();
        }

        // We handle our fragment here

        // finding the retained fragment on restarts
        android.app.FragmentManager fm = getFragmentManager();
        mRetainedFragment = (ComboInputFragment) fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        // Create the fragment and data the first time we go through onCreate()
        if (mRetainedFragment == null) {
            // instanciate the fragment
            mRetainedFragment = new ComboInputFragment();
            // we add the Fragment to the list of Fragments handled by our FragmentManager
            fm.beginTransaction().add(mRetainedFragment, TAG_RETAINED_FRAGMENT).commit();
            // we set our initial Data
            mRetainedFragment.setData(comboInput);
        }

        // We setup the variables used throughout our functions
        comboInput =mRetainedFragment.getData(); // If it's the first onCreate call, it's a new ArrayList<String> or w/e we got fed by intent.extras, else, it contains the last inputs we stored in mRetainedFragment
        inputsDrawn = 0;
        currentLayout = (LinearLayout)findViewById(R.id.Drawing1); // we'll draw on Drawing1 first

        // TreeObserver : to call a function as soon as the layout it is associated with is drawn
        ViewTreeObserver vto = currentLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                currentLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                drawingHeight = currentLayout.getHeight();
                drawingWidth = currentLayout.getWidth();

                if (comboInput.size() != 0) {
                    drawComboInput();
                }

            }
        });


        // We start our activity with Directional Inputs ready to go
        Button directionsButton = (Button) findViewById(R.id.DirectionsTabSelector);
        Button buttonsButton = (Button) findViewById(R.id.ButtonsTabSelector);
        Button etcButton = (Button) findViewById(R.id.EtcTabSelector);
        activateLayout(findViewById(R.id.DirectionsTabSelector));


        // Make it look nice
        Typeface typefaceButtons =  Typeface.createFromAsset(getAssets(), "fonts/tarrgetengrave.ttf");
        directionsButton.setTypeface(typefaceButtons);
        buttonsButton.setTypeface(typefaceButtons);
        etcButton.setTypeface(typefaceButtons);

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d("NewCombo", "onDestroy Called");
        mRetainedFragment.setData(comboInput);
    }

    protected void setLongClickListeners() {
        LinearLayout layoutV = (LinearLayout) findViewById(R.id.PanelDirectionsV);
        for (int i = 0; i < layoutV.getChildCount(); i++) {
            View view = layoutV.getChildAt(i);
            if (view.getTag().toString().equals("sep")) {
                // do nothing
            } else {
                LinearLayout layoutH = (LinearLayout) layoutV.getChildAt(i);
                for (int j = 0; j < layoutH.getChildCount(); j++) {
                    View view1 = layoutH.getChildAt(j);
                    if (view1.getTag().toString().equals("sep")) {
                        // do nothing
                    } else {
                        Button b = (Button) layoutH.getChildAt(j);
                        b.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                Button b = (Button) v;
                                String input = b.getTag().toString();
                                if (comboInput.size() == 0) {
                                    processInputSizes(); // we call it once, when adding our first input
                                }

                                switch (input) {
                                    case "ub":
                                        input = "UB";
                                        break;
                                    case "u":
                                        input = "U";
                                        break;
                                    case "uf":
                                        input = "UF";
                                        break;
                                    case "b":
                                        input = "B";
                                        break;
                                    case "n":
                                        input = "N";
                                        break;
                                    case "f":
                                        input = "F";
                                        break;
                                    case "db":
                                        input = "DB";
                                        break;
                                    case "d":
                                        input = "D";
                                        break;
                                    case "df":
                                        input = "DF";
                                        break;
                                    default:
                                        input = "default";
                                }

                                comboInput.add(input);
                                addVisual(input);
                                return true;
                            }
                        });

                    }
                }
            }
        }
    }

    protected void activateLayout (View view){
        Button b = (Button) view;
        String buttonText = b.getTag().toString();

        LinearLayout layout;
        LinearLayout selecLayout;
        Button selector;

        switch (buttonText){
            case "Directions" :
                layout = (LinearLayout) findViewById(R.id.PanelDirectionsV);
                layout.setVisibility(View.VISIBLE);
                layout = (LinearLayout) findViewById(R.id.PanelBoutonsV);
                layout.setVisibility(View.INVISIBLE);
                layout = (LinearLayout) findViewById(R.id.PanelEtcV);
                layout.setVisibility(View.INVISIBLE);
                //toast = Toast.makeText(getApplicationContext(), "Click sur Directions", Toast.LENGTH_SHORT);
                //toast.show();
                selector = (Button) findViewById(R.id.DirectionsTabSelector);
                selector.setAlpha(1f);
                selector = (Button) findViewById(R.id.ButtonsTabSelector);
                selector.setAlpha(.5f);
                selector = (Button) findViewById(R.id.EtcTabSelector);
                selector.setAlpha(.5f);
                selecLayout = (LinearLayout) findViewById(R.id.Inputs);
                selecLayout.setBackgroundColor(getResources().getColor(R.color.darkBlue));

                break;
            case "Buttons" :
                layout = (LinearLayout) findViewById(R.id.PanelDirectionsV);
                layout.setVisibility(View.INVISIBLE);
                layout = (LinearLayout) findViewById(R.id.PanelBoutonsV);
                layout.setVisibility(View.VISIBLE);
                layout = (LinearLayout) findViewById(R.id.PanelEtcV);
                layout.setVisibility(View.INVISIBLE);
                //toast = Toast.makeText(getApplicationContext(), "Click sur Buttons", Toast.LENGTH_SHORT);
                //toast.show();
                selector = (Button) findViewById(R.id.DirectionsTabSelector);
                selector.setAlpha(.5f);
                selector = (Button) findViewById(R.id.ButtonsTabSelector);
                selector.setAlpha(1f);
                selector = (Button) findViewById(R.id.EtcTabSelector);
                selector.setAlpha(.5f);;
                selecLayout = (LinearLayout) findViewById(R.id.Inputs);
                selecLayout.setBackgroundColor(getResources().getColor(R.color.darkRed));
                break;
            case "Etc" :
                layout = (LinearLayout) findViewById(R.id.PanelDirectionsV);
                layout.setVisibility(View.INVISIBLE);
                layout = (LinearLayout) findViewById(R.id.PanelBoutonsV);
                layout.setVisibility(View.INVISIBLE);
                layout = (LinearLayout) findViewById(R.id.PanelEtcV);
                layout.setVisibility(View.VISIBLE);
                //toast = Toast.makeText(getApplicationContext(), "Click sur Etc", Toast.LENGTH_SHORT);
                //toast.show();
                selector = (Button) findViewById(R.id.DirectionsTabSelector);
                selector.setAlpha(.5f);
                selector = (Button) findViewById(R.id.ButtonsTabSelector);
                selector.setAlpha(.5f);
                selector = (Button) findViewById(R.id.EtcTabSelector);
                selector.setAlpha(1f);;
                selecLayout = (LinearLayout) findViewById(R.id.Inputs);
                selecLayout.setBackgroundColor(getResources().getColor(R.color.darkPurple));
                break;
        }


    }

    protected void drawComboInput() {
        Log.d("NewCombo", "drawComboInput Called, contenu de ComboInput : " + comboInput.toString());
        processInputSizes(); // we call it once when drawing a pre-existing combo
        for (int i=0; i<comboInput.size();i++){
            String input = comboInput.get(i);
            switch (input) { // shortcuts are NOT inputs, we must break them down in order to keep a 1:1 ratio between combo list and drawn inputs
                case "QCF" :
                    input = "d";
                    comboInput.add(input);
                    addVisual(input);
                    input = "df";
                    comboInput.add(input);
                    addVisual(input);
                    input ="f";
                    break;
                case "QCB" :
                    input = "d";
                    comboInput.add(input);
                    addVisual(input);
                    input = "db";
                    comboInput.add(input);
                    addVisual(input);
                    input ="b";
                    break;
                case "CD" :
                    input = "f";
                    comboInput.add(input);
                    addVisual(input);
                    input = "n";
                    comboInput.add(input);
                    addVisual(input);
                    input = "d";
                    comboInput.add(input);
                    addVisual(input);
                    input ="df";
                    break;
                default : break;
            }
            addVisual(input);
        }
    }

    protected void addInput(View view) {
        Button b = (Button) view;
        String input = b.getTag().toString();
        if (comboInput.size() == 0) {
            processInputSizes(); // we call it once, when adding our first input
        }

        switch (input) { // shortcuts are NOT inputs, we must break them down in order to keep a 1:1 ratio between combo list and drawn inputs
            case "QCF" :
                input = "d";
                comboInput.add(input);
                addVisual(input);
                input = "df";
                comboInput.add(input);
                addVisual(input);
                input ="f";
                break;
            case "QCB" :
                input = "d";
                comboInput.add(input);
                addVisual(input);
                input = "db";
                comboInput.add(input);
                addVisual(input);
                input ="b";
                break;
            case "CD" :
                input = "f";
                comboInput.add(input);
                addVisual(input);
                input = "n";
                comboInput.add(input);
                addVisual(input);
                input = "d";
                comboInput.add(input);
                addVisual(input);
                input ="df";
                break;
            default : break;
        }

        comboInput.add(input);
        //toast = Toast.makeText(getApplicationContext(), "conboInput : " + comboInput.toString(), Toast.LENGTH_SHORT);
        //toast.show();
        addVisual(input);
        //toast = Toast.makeText(getApplicationContext(), "conboInput : " + comboInput.toString(), Toast.LENGTH_SHORT);
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

    protected void switchBackDrawingLayout(Integer nb){
        switch (nb) {
            case 2 :
                currentLayout.setVisibility(LinearLayout.GONE);
                currentLayout = (LinearLayout)findViewById(R.id.Drawing1);
                break;
            case 3 :
                currentLayout.setVisibility(LinearLayout.GONE);
                currentLayout = (LinearLayout)findViewById(R.id.Drawing2);
                break;

            case 4 :
                currentLayout.setVisibility(LinearLayout.GONE);
                currentLayout = (LinearLayout)findViewById(R.id.Drawing3);
                break;

            case 5 :
                currentLayout.setVisibility(LinearLayout.GONE);
                currentLayout = (LinearLayout)findViewById(R.id.Drawing4);
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
            case "RA" : return getResources().getDrawable(R.drawable.drawra);
            case "Into" : return getResources().getDrawable(R.drawable.into);
            case "WS" : return getResources().getDrawable(R.drawable.ws);
            case "WR" : return getResources().getDrawable(R.drawable.drawwr);
            case "SS" : return getResources().getDrawable(R.drawable.drawss);
            default: return getResources().getDrawable(R.drawable.drawdefault);
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

    protected void resetWindow(View view) {

        // On supprime les imgView, et on rend les Drawing2-5 GONE
        currentLayout = (LinearLayout)findViewById(R.id.Drawing5);
        currentLayout.removeAllViewsInLayout();
        currentLayout.setVisibility(LinearLayout.GONE);
        currentLayout = (LinearLayout)findViewById(R.id.Drawing4);
        currentLayout.removeAllViewsInLayout();
        currentLayout.setVisibility(LinearLayout.GONE);
        currentLayout = (LinearLayout)findViewById(R.id.Drawing3);
        currentLayout.removeAllViewsInLayout();
        currentLayout.setVisibility(LinearLayout.GONE);
        currentLayout = (LinearLayout)findViewById(R.id.Drawing2);
        currentLayout.removeAllViewsInLayout();
        currentLayout.setVisibility(LinearLayout.GONE);
        currentLayout = (LinearLayout)findViewById(R.id.Drawing1);
        currentLayout.removeAllViewsInLayout();

        inputsDrawn = 0;

        comboInput = new ArrayList<>();
    }

    protected void removeLastInput (View view) {
        if (comboInput.size() != 0) {
            comboInput.remove(comboInput.size() - 1);
            if (currentLayout.getChildCount() != 0) {
                currentLayout.removeViewAt(currentLayout.getChildCount() - 1);
            } else { // We go back to the previous Drawing Layout

                Log.d("removeLastInput", "We're in the else part of remove");

                switch (currentLayout.getTag().toString()) {
                    case "Drawing2" :
                        Log.d("removeLastInput", "case Drawing 2");
                        switchBackDrawingLayout(2);
                        break;
                    case "Drawing3" :
                        Log.d("removeLastInput", "case Drawing 3");
                        switchBackDrawingLayout(3);
                        break;
                    case "Drawing4" :
                        Log.d("removeLastInput", "case Drawing 4");
                        switchBackDrawingLayout(4);
                        break;
                    case "Drawing5" :
                        Log.d("removeLastInput", "case Drawing 5");
                        switchBackDrawingLayout(5);
                        break;
                    default:break;

                }
                // And we remove it's last child
                currentLayout.removeViewAt(currentLayout.getChildCount() - 1);
            }
            // We have one less drawn input on our screen.
            inputsDrawn --;
        }

    }

    protected void test (View view) {
        String toastText = "Largeur de currentLayout : " + currentLayout.getWidth() + ", hauteur de currentLayout : " + currentLayout.getHeight();
        toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void ToHome (View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
    public void ToHome () {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    protected void SaveCombo (View view) {
        Intent intent = new Intent(this, SavingCombo.class);
        intent.putExtra("input", comboInput);
        startActivity(intent);
    }

    public void onBackPressed() {
        ToHome();
    }

}
