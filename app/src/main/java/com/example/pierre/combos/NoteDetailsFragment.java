package com.example.pierre.combos;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by pierre on 26/08/2017.
 */

public class NoteDetailsFragment extends Fragment {

    private String tag;
    private String id;
    private String note;
    private String character;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retains this fragment
        setRetainInstance(true);
    }

    public void setNoteString (String dataIn){
        this.note = dataIn;
    }

    public void setTagString (String tag) {
        this.tag = tag;
    }

    public void setIdString (String id) {
        this.id = id;
    }

    public void setCharacterString (String character) {
        this.character = character;
    }

    public String getTagString () {
        return this.tag;
    }

    public String getNoteString () {
        return this.note;
    }

    public String getIdString() {
        return this.id;
    }

    public String getCharacterString() {
        return this.character;
    }

}
