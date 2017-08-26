package com.example.pierre.combos;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by pierre on 26/08/2017.
 */

public class ComboInputFragment extends Fragment {

    private ArrayList<String> data;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retains this fragment
        setRetainInstance(true);
    }

    public void setData (ArrayList<String> dataIn){
        this.data = dataIn;
    }

    public ArrayList<String> getData () {
        return this.data;
    }
}
