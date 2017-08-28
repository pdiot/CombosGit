package com.example.pierre.combos;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by pierre on 26/08/2017.
 */

public class ComboDetailsFragment extends Fragment {

    private ArrayList<String> inputArray;
    private String tag;
    private String id;
    private String damage;
    private String character;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retains this fragment
        setRetainInstance(true);
    }

    public void setInputArray (ArrayList<String> dataIn){
        this.inputArray = dataIn;
    }

    public void setTagString (String tag) {
        this.tag = tag;
    }

    public void setIdString (String id) {
        this.id = id;
    }

    public void setDamageString (String damage) {
        this.damage = damage;
    }

    public void setCharacterString (String character) {
        this.character = character;
    }

    public ArrayList<String> getInputArray () {
        return this.inputArray;
    }

    public String getTagString () {
        return this.tag;
    }

    public String getDamageString () {
        return this.damage;
    }

    public String getIdString() {
        return this.id;
    }

    public String getCharacterString() {
        return this.character;
    }
}
