package com.gdbc.clubdeportivo.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DB = "Clubdeportivo"

class BDatos(context: Context) : SQLiteOpenHelper(context, DB, null, 2) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_CLIENTE)
        db?.execSQL(CREATE_TABLE_MOROSO)
        db?.execSQL(INSERT_TABLE_CLIENTE)
        db?.execSQL(INSERT_TABLE_MOROSO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS cliente")
        db?.execSQL("DROP TABLE IF EXISTS moroso")
        onCreate(db)
    }

    companion object {

        private const val CREATE_TABLE_CLIENTE = "CREATE TABLE cliente" +
                "(id_cliente INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "dni TEXT NOT NULL UNIQUE," +
                "tiene_apto_fisico INTEGER NOT NULL," +
                "tipo_cliente TEXT NOT NULL," +
                "fecha_registro DATE NOT NULL);"

        private const val INSERT_TABLE_CLIENTE = "INSERT INTO cliente VALUES" +
                "(NULL, 'Juan', 'Perez', '12345678', 1, 'socio', '2024-04-01')," +
                "(NULL, 'Ana', 'Gomez', '27654321', 1, 'socio', '2024-03-15')," +
                "(NULL, 'Carlos', 'Lopez', '11223344', 1, 'socio', '2024-02-28')," +
                "(NULL, 'Maria', 'Martinez', '44332211', 1, 'socio', '2024-01-30')," +
                "(NULL, 'Luis', 'Garcia', '35667788', 1, 'socio', '2024-04-20')," +
                "(NULL, 'Elena', 'Sanchez', '49887766', 1, 'socio', '2024-03-10')," +
                "(NULL, 'Miguel', 'Fernandez', '66554433', 1, 'no_socio', '2024-02-18')," +
                "(NULL, 'Marcelo', 'Sanchez', '36544738', 1, 'no_socio', '2024-02-18');"

        private const val CREATE_TABLE_MOROSO = "CREATE TABLE moroso" +
                "(id_moroso INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cliente INTEGER NOT NULL," +
                "FOREIGN KEY(id_cliente) REFERENCES cliente(id_cliente));"

        private const val INSERT_TABLE_MOROSO = "INSERT INTO moroso VALUES" +
                "(NULL, 1)," +
                "(NULL, 2)," +
                "(NULL, 3)," +
                "(NULL, 4)," +
                "(NULL, 5)," +
                "(NULL, 6);"

    }
}
