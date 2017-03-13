
package com.example.laboratorio.classwork5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "ElTagListView";
    private DataEntryDAO mDataEntryDao;
    private ArrayList<DataEntry> Entries;
    private CustomAdapter customAdapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list=(ListView) findViewById(R.id.ListView);

        mDataEntryDao = new DataEntryDAO(this);

        Entries = mDataEntryDao.getAllEntrys();

        customAdapter = new CustomAdapter(this,Entries);

        list.setAdapter(customAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataEntry dataEntry = (DataEntry) list.getItemAtPosition(position);
                Log.d(TAG,"Click en Botton "+dataEntry.id);
                Intent iview= new Intent(MainActivity.this,ViewActivity.class);
                Bundle mBundle= new Bundle();
                mBundle.putSerializable("Entryview",dataEntry);
                iview.putExtras(mBundle);
                startActivityForResult(iview,2);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this,ViewActivity.class);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onDestroy(){
        mDataEntryDao.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
            if(resultCode== Activity.RESULT_OK){
                DataEntry de= (DataEntry) data.getSerializableExtra("entry");
                if(requestCode==1){
                    mDataEntryDao.addDataEntry(de);
                }else{
                    mDataEntryDao.updateEntry(de);

                }

                Entries = mDataEntryDao.getAllEntrys();
                customAdapter.setData(Entries);
                ((CustomAdapter)list.getAdapter()).notifyDataSetChanged();
            }

    }

    public void OnClickButtonRow(View view){
        DataEntry dataEntry= (DataEntry) view.getTag();
        Log.d(TAG,"Click en "+dataEntry.id);
        mDataEntryDao.deleteEntry(dataEntry);
        Entries = mDataEntryDao.getAllEntrys();
        customAdapter.setData(Entries);
        ((CustomAdapter)list.getAdapter()).notifyDataSetChanged();
    }
}
