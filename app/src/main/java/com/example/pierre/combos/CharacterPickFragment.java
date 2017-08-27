package com.example.pierre.combos;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by pierre on 26/08/2017.
 */

public class CharacterPickFragment extends Fragment {

    private String data;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retains this fragment
        setRetainInstance(true);
    }

    public void setData (String dataIn){
        this.data = dataIn;
    }

    public String getData () {
        return this.data;
    }
}
