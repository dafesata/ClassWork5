
package com.example.laboratorio.classwork5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by daniel on 12/03/17.
 */

public class DataEntryDAO {
    public String TAG = Constanst.TAG;
    private Context context;
    private DatabaseHandler mDBHandler;
    private SQLiteDatabase mDataBase;

    public DataEntryDAO(Context context) {
        this.context = context;
        mDBHandler = new DatabaseHandler(context);
        open();
    }


    public void open(){
        mDataBase= mDBHandler.getWriteDatabase();
    }

    public void close(){
        mDBHandler.close();
    }

    public long addDataEntry (DataEntry entry){
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_FIELD1,entry.titulo);
        values.put(DatabaseHandler.KEY_FIELD2, entry.fecha);
        values.put(DatabaseHandler.KEY_FIELD3,entry.texto);

        long index=  mDataBase.insert(DatabaseHandler.TABLE,null,values);
        Log.d(TAG,"addDataEntry return " +  index);
        return index;
    }

    public DataEntry GetEntryDAO(int id){
        Cursor cursor= mDataBase.query(DatabaseHandler.TABLE,
                new String[]{DatabaseHandler.KEY_ID,DatabaseHandler.KEY_FIELD1,DatabaseHandler.KEY_FIELD2,DatabaseHandler.KEY_FIELD3},
                DatabaseHandler.KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        DataEntry entry= new DataEntry(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        cursor.close();
        return entry;

    }

    public ArrayList<DataEntry> getAllEntrys() {
        ArrayList<DataEntry> Entrylist = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE;


        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataEntry entry= new DataEntry(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                // Adding contact to list
                Entrylist.add(entry);
            } while (cursor.moveToNext());
        }

        // return contact list
        return Entrylist;
    }

    public int updateEntry(DataEntry Entry) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_FIELD1, Entry.titulo);
        values.put(DatabaseHandler.KEY_FIELD2, Entry.fecha);
        values.put(DatabaseHandler.KEY_FIELD3, Entry.texto);

        // updating row
        return mDataBase.update(DatabaseHandler.TABLE, values, DatabaseHandler.KEY_ID + " =?",
                new String[] { String.valueOf(Entry.id) });
    }

    public void deleteEntry(DataEntry Entry) {

        mDataBase.delete(DatabaseHandler.TABLE, DatabaseHandler.KEY_ID + " =?",
                new String[] { String.valueOf(Entry.id) });
    }

}
