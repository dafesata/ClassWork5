
package com.example.laboratorio.classwork5;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daniel on 12/03/17.
 */

public class DataEntry implements Serializable {
    int id;
    String titulo,texto, fecha;

    public DataEntry() {
    }

    public DataEntry(int id, String field1, String field2, String field3) {
        this.id = id;
        this.titulo = field1;
        this.fecha = field2;
        this.texto=field3;
    }


    public DataEntry(String titulo,String texto) {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.titulo = titulo;
        this.texto = texto;
        this.fecha = format.format(curDate);
    }
}
