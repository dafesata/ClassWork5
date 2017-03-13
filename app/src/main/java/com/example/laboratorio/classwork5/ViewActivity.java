
package com.example.laboratorio.classwork5;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewActivity extends AppCompatActivity {
    EditText texto,titulo,fecha;
    DataEntry dataEntry;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        titulo= (EditText) findViewById(R.id.editTitulo);
        fecha=(EditText) findViewById(R.id.editFecha);
        texto= (EditText) findViewById(R.id.editTexto);
        bundle= getIntent().getExtras();
        if(bundle!=null){

            dataEntry = (DataEntry) bundle.getSerializable("Entryview");
            titulo.setText(dataEntry.titulo);
            texto.setText(dataEntry.texto);
            fecha.setText(dataEntry.fecha);
        }else{
            Date curDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            fecha.setText(format.format(curDate));

        }
        fecha.setFocusable(false);
        fecha.setFocusableInTouchMode(false);

    }

    public void onClickAceptar(View view) {
        DataEntry de;
        if(TextUtils.isEmpty(titulo.getText().toString())){
            titulo.setError("No puede estar vacio");
        }else{
            if(TextUtils.isEmpty(texto.getText().toString())){
                texto.setError("No puede estar vacio");
            }else{
                if (bundle!=null) {
                    de = new DataEntry(dataEntry.id, titulo.getText().toString(), fecha.getText().toString(), texto.getText().toString());

                }else{
                    de = new DataEntry(titulo.getText().toString(), texto.getText().toString());
                }
                Intent i = getIntent();
                i.putExtra("entry", de);
                setResult(Activity.RESULT_OK, i);
                finish();

            }
        }

    }
}
