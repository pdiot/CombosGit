package com.example.pierre.combos;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExportImport extends Activity {

    Toast toast;

    public static final String COMBO_FILE_NAME = "Tekken7CompanionCombos.txt";
    public static final String NOTES_FILE_NAME = "Tekken7CompanionNotes.txt";

    ComboDatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_import);
        toast = new Toast(this);

        myDbHelper = new ComboDatabaseHelper(this);
    }

    protected ArrayList<String> parseLine (String input){

        Log.d("Log Import", "dans parseLine, input vaut : " + input);
        String[] tmpArr = input.split(";!;");
        Log.d("Log Import", "dans parseLine, tmpArr.length vaut : " + tmpArr.length);
        ArrayList<String> ret = new ArrayList<>();
        for (int i = 0; i < tmpArr.length; i++) {
            String tmp2 = tmpArr[i];
            Log.d("Log Import", "dans parseLine, i = " + i + ", tmp2 =  "  + tmp2);
            ret.add(tmp2);
        }
        return ret;
    }

    protected void exportCombos (View view) {

        String filename = COMBO_FILE_NAME;
        String baseFolder;
        OutputStreamWriter osw;
        // check if external storage is available
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            baseFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

            File file = new File(baseFolder + File.separator + filename);
            file.getParentFile().mkdirs();
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);

                SQLiteDatabase db = myDbHelper.getReadableDatabase();

                String[] projection = {
                        DatabaseContract.ComboTable.COLUMN_NAME_TAG,
                        DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,
                        DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER,
                        DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE
                };

                String sortOrder = DatabaseContract.ComboTable._ID + " ASC";

                Cursor cursor = db.query(
                        DatabaseContract.ComboTable.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder
                );

                ArrayList<String[]> results = new ArrayList<>();
                while (cursor.moveToNext())  {
                    String tagString = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_TAG));
                    String inputString = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS));
                    String damageString = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE));
                    String characterString = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER));
                    String[] resultsString = new String[] {tagString, inputString, damageString, characterString};
                    results.add(resultsString);
                }
                cursor.close();

                osw = new OutputStreamWriter(fos);

                for (int i=0; i<results.size(); i++) {
                    String[] combo = results.get(i);

                    String comboCsvLine = "";
                    // our separator will be ";!;" because why not (and I'd like to use the same one for notes too, where one can use ! or ;, I assume using ;!; is not a common enough thing that it will be a problem)
                    comboCsvLine += combo[0] +";!;" + combo[1] +";!;" +combo[2] +";!;" + combo[3];
                    //              tag     ;!;     inputs      ;!;     damage      ;!; character
                    osw.append(comboCsvLine);
                    osw.append(System.getProperty("line.separator")); // new line
                    osw.flush();
                }
                osw.close();
                fos.close();
                db.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                toast = toast.makeText(this, "Can't open the file, make sure all authorizations are given to the app !", Toast.LENGTH_SHORT);
                toast.show();
            } catch (IOException e) {
                e.printStackTrace();
                toast = toast.makeText(this, "Can't open the file, make sure all authorizations are given to the app !", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        else {  // No use writing to internal storage since we want to be able to share our files later on
            toast = toast.makeText(this, "Can't open the file, make sure all authorizations are given to the app !", Toast.LENGTH_LONG);
        }




    }

    protected void importCombos (View view) {

        //setup our base folder
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File baseFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


            //Get the text file
            File file = new File(baseFolder,COMBO_FILE_NAME);

            //Read text from file

            ArrayList<String> combos = new ArrayList<String>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    combos.add(line);
                }
                br.close();
            }
            catch (IOException e) {
                Log.d("Log Impport", "Can't open file");
                toast = toast.makeText(this, "Can't find the file, make sure it's named " + COMBO_FILE_NAME, Toast.LENGTH_SHORT);
                toast.show();
            }

            // We have our file parsed into our ArrayList
            // Now we must work with it to import its content into our database

            SQLiteDatabase db = myDbHelper.getWritableDatabase();

            /* We have an ArrayList containing Strings on the following model :
            TAG                     String, text, not controlled
            ;!;
            INPUTS                  String, no need to parse it to get rid of [ , or spaces, since it already is in the format used by our db
            ;!;
            DAMAGE                  String, one number
            ;!;
            CHARACTER               String, text, controlled
            */

            Log.d("Log Import", "combos content : " + combos.toString());
            for (int i = 0; i < combos.size(); i++) { // for each String in our ArrayList combos

                // First, we parse our line around the ";!;" separator
                ArrayList<String> databaseColumnValues = parseLine(combos.get(i));
                Log.d("Log Import", "i : " + i);
                Log.d("Log Import", "combos(i) : " + combos.get(i));
                Log.d("Log Import", "databaseColumn : " + databaseColumnValues.toString());
                /* We now have :
                databaseColumnValues, containing TAG, INPUTS, DAMAGE, CHARACTER
                we can insert those values in our database
                */
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.ComboTable.COLUMN_NAME_TAG,databaseColumnValues.get(0));
                values.put(DatabaseContract.ComboTable.COLUMN_NAME_INPUTS,databaseColumnValues.get(1));
                values.put(DatabaseContract.ComboTable.COLUMN_NAME_DAMAGE,databaseColumnValues.get(2));
                values.put(DatabaseContract.ComboTable.COLUMN_NAME_CHARACTER,databaseColumnValues.get(3));

                long newRowId = db.insert(DatabaseContract.ComboTable.TABLE_NAME, null, values);
                if (newRowId > 0) {
                    Log.d("Log Import", "OK");
                    toast = toast.makeText(this, "Import OK", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Log.d("Log Import", "KO");
                    toast = toast.makeText(this, "Something went wrong with the import :( ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            db.close();
        } else {
            Log.d("Log Import", "external storage not available" );
            toast = toast.makeText(this, "Can't open the file, make sure all authorizations are given to the app !", Toast.LENGTH_LONG);
        }
    }


    protected void exportNotes (View view) {

        String filename = NOTES_FILE_NAME;
        String baseFolder;
        OutputStreamWriter osw;
        // check if external storage is available
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            baseFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

            File file = new File(baseFolder + File.separator + filename);
            file.getParentFile().mkdirs();
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);

                SQLiteDatabase db = myDbHelper.getReadableDatabase();

                String[] projection = {
                        DatabaseContract.NotesTables.COLUMN_NAME_TAG,
                        DatabaseContract.NotesTables.COLUMN_NAME_NOTE,
                        DatabaseContract.NotesTables.COLUMN_NAME_CHARACTER
                };

                String sortOrder = DatabaseContract.NotesTables._ID + " ASC";

                Cursor cursor = db.query(
                        DatabaseContract.NotesTables.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder
                );

                ArrayList<String[]> results = new ArrayList<>();
                while (cursor.moveToNext())  {
                    String tagString = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseContract.NotesTables.COLUMN_NAME_TAG));
                    String noteString = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseContract.NotesTables.COLUMN_NAME_NOTE));
                    String characterString = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseContract.NotesTables.COLUMN_NAME_CHARACTER));
                    String[] resultsString = new String[] {tagString, noteString, characterString};
                    results.add(resultsString);
                }
                cursor.close();

                osw = new OutputStreamWriter(fos);

                for (int i=0; i<results.size(); i++) {
                    String[] combo = results.get(i);

                    String comboCsvLine = "";
                    // our separator will be ";!;" because why not (and I'd like to use the same one for notes too, where one can use ! or ;, I assume using ;!; is not a common enough thing that it will be a problem)
                    comboCsvLine += combo[0] +";!;" + combo[1] +";!;" + combo[2];
                    //              tag     ;!;       note       ;!;  character
                    osw.append(comboCsvLine);
                    osw.append(System.getProperty("line.separator")); // new line
                    osw.flush();
                }
                osw.close();
                fos.close();
                db.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {  // No use writing to internal storage since we want to be able to share our files later on
            // Do nothing
            toast = toast.makeText(this, "Can't open the file, make sure all authorizations are given to the app !", Toast.LENGTH_LONG);
        }




    }

    protected void importNotes (View view) {

        //setup our base folder
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File baseFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


            //Get the text file
            File file = new File(baseFolder,NOTES_FILE_NAME);

            //Read text from file

            ArrayList<String> combos = new ArrayList<String>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    combos.add(line);
                }
                br.close();
            }
            catch (IOException e) {
                Log.d("Log Impport", "Can't open file");
                toast = toast.makeText(this, "No file to be found ", Toast.LENGTH_SHORT);
                toast.show();
            }

            // We have our file parsed into our ArrayList
            // Now we must work with it to import its content into our database

            SQLiteDatabase db = myDbHelper.getWritableDatabase();

            /* We have an ArrayList containing Strings on the following model :
            TAG                     String, text, not controlled
            ;!;
            NOTE                    String, no need to parse it since it's simple text
            ;!;
            CHARACTER               String, text, controlled
            */

            for (int i = 0; i < combos.size(); i++) { // for each String in our ArrayList combos

                // First, we parse our line around the ";!;" separator
                ArrayList<String> databaseColumnValues = parseLine(combos.get(i));
                /* We now have :
                databaseColumnValues, containing TAG, NOTE, CHARACTER
                we can insert those values in our database
                */
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.NotesTables.COLUMN_NAME_TAG,databaseColumnValues.get(0));
                values.put(DatabaseContract.NotesTables.COLUMN_NAME_NOTE,databaseColumnValues.get(1));
                values.put(DatabaseContract.NotesTables.COLUMN_NAME_CHARACTER,databaseColumnValues.get(2));

                long newRowId = db.insert(DatabaseContract.NotesTables.TABLE_NAME, null, values);
                if (newRowId > 0) {
                    Log.d("Log Import", "OK");
                    toast = toast.makeText(this, "Import OK", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Log.d("Log Import", "KO");
                    toast = toast.makeText(this, "Something went wrong with the import :( ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            db.close();
        } else {
            Log.d("Log Import", "external storage not available" );
        }
    }

    public void ToHome (View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

}
