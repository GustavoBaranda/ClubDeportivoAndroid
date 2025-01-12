package com.gdbc.clubdeportivo.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DB = "Clubdeportivo"

class BDatos(context: Context) : SQLiteOpenHelper(context, DB, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_CLIENTE)
        db?.execSQL(CREATE_TABLE_USUARIO)
        db?.execSQL(CREATE_TABLE_MOROSO)
        db?.execSQL(CREATE_TABLE_ACTIVIDAD)
        db?.execSQL(CREATE_TABLE_PAGO)
        db?.execSQL(CREATE_TABLE_INSCRIPCION)
        db?.execSQL(INSERT_TABLE_CLIENTE)
        db?.execSQL(INSERT_TABLE_USUARIO)
        db?.execSQL(INSERT_TABLE_MOROSO)
        db?.execSQL(INSERT_TABLE_ACTIVIDAD)
        db?.execSQL(INSERT_TABLE_PAGO)
        db?.execSQL(INSERT_TABLE_INSCRIPCION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS cliente")
        db?.execSQL("DROP TABLE IF EXISTS usuario")
        db?.execSQL("DROP TABLE IF EXISTS moroso")
        db?.execSQL("DROP TABLE IF EXISTS actividad")
        db?.execSQL("DROP TABLE IF EXISTS pago")
        db?.execSQL("DROP TABLE IF EXISTS inscripcion")
        onCreate(db)
    }

    companion object {
        private const val CREATE_TABLE_CLIENTE = "CREATE TABLE cliente" +
                "(id_cliente INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "dni TEXT NOT NULL UNIQUE," +
                "tiene_apto_fisico INTEGER NOT NULL," +
                "tipo_cliente TEXT NOT NULL CHECK (tipo_cliente IN ('socio', 'no socio'))," +
                "fecha_registro DATE NOT NULL," +
                "id_usuario INTEGER NOT NULL," +
                "FOREIGN KEY(id_usuario) REFERENCES usuario(id_usuario) ON DELETE SET NULL);"

        private const val INSERT_TABLE_CLIENTE = "INSERT INTO cliente VALUES" +
                "(NULL, 'Juan', 'Perez', '12345678', 1, 'socio', '2024-04-01', 1)," +
                "(NULL, 'Ana', 'Gomez', '27654321', 1, 'socio', '2024-03-15', 2)," +
                "(NULL, 'Carlos', 'Lopez', '11223344', 1, 'socio', '2024-02-28', 3)," +
                "(NULL, 'Maria', 'Martinez', '44332211', 1, 'socio', '2024-01-30', 4)," +
                "(NULL, 'Luis', 'Garcia', '35667788', 1, 'socio', '2024-04-20', 5)," +
                "(NULL, 'Elena', 'Sanchez', '49887766', 1, 'socio', '2024-03-10', 6)," +
                "(NULL, 'Miguel', 'Fernandez', '66554433', 1, 'no socio', '2024-02-18', 7)," +
                "(NULL, 'Marcelo', 'Sanchez', '36544738', 1, 'no socio', '2024-02-18', 8);"

        private const val CREATE_TABLE_USUARIO = "CREATE TABLE usuario" +
                "(id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "alias TEXT NOT NULL UNIQUE," +
                "contrasena TEXT NOT NULL," +
                "rol TEXT NOT NULL CHECK (rol IN ('cliente', 'empleado')));"


        private const val INSERT_TABLE_USUARIO = "INSERT INTO usuario VALUES " +
                "(NULL, 'cliente', 'cliente', 'cliente')," +
                "(NULL, 'agomez', 'pass456', 'cliente')," +
                "(NULL, 'clopez', 'pass789', 'cliente')," +
                "(NULL, 'mmartinez', 'pass321', 'cliente')," +
                "(NULL, 'lgarcia', 'pass654', 'cliente')," +
                "(NULL, 'esanchez', 'pass987', 'cliente')," +
                "(NULL, 'mfernandez', 'pass147', 'cliente')," +
                "(NULL, 'msanchez', 'pass258', 'cliente')," +
                "(NULL, 'admin', 'admin', 'empleado')," +
                "(NULL, 'mgalim', 'pelakiller', 'empleado')," +
                "(NULL, 'alebadi', 'batman', 'empleado')," +
                "(NULL, 'cescobar', 'asda1234', 'empleado')," +
                "(NULL, 'ebaba', 'meugenia', 'empleado')," +
                "(NULL, 'gbaranda', 'jslover', 'empleado');"

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

        private const val CREATE_TABLE_ACTIVIDAD = "CREATE TABLE actividad" +
                "(id_actividad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_actividad TEXT NOT NULL," +
                "precio_diario REAL NOT NULL CHECK (precio_diario >= 0));"

        private const val INSERT_TABLE_ACTIVIDAD = "INSERT INTO actividad VALUES " +
                "(NULL, 'Natación', 11000.50), " +
                "(NULL, 'Yoga', 9000.00), " +
                "(NULL, 'Fútbol', 8500.00), " +
                "(NULL, 'Gimnasio', 8500.75), " +
                "(NULL, 'Spinning', 9100.00);"

        private const val CREATE_TABLE_PAGO = "CREATE TABLE pago" +
                "(id_pago INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "monto REAL NOT NULL CHECK (monto >= 0), " +
                "metodo_pago TEXT NOT NULL CHECK (metodo_pago IN ('efectivo', 'tarjeta')), " +
                "id_cliente INTEGER NOT NULL, " +
                "tipo_pago TEXT NOT NULL CHECK (tipo_pago IN ('mensual', 'diario')), " +
                "id_actividad INTEGER, " +
                "cant_cuotas INTEGER, " +
                "fecha_pago TEXT NOT NULL, " +
                "FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente), " +
                "FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad));"

        private const val INSERT_TABLE_PAGO = "INSERT INTO pago VALUES " +
                "(NULL, 30000.00, 'efectivo', 1, 'mensual', NULL, NULL, '2024-09-01'), " +
                "(NULL, 36000.00, 'tarjeta', 2, 'mensual', NULL, 3, '2024-09-01'), " +
                "(NULL, 36000.00, 'tarjeta', 3, 'mensual', NULL, 1, '2024-09-02'), " +
                "(NULL, 30000.00, 'efectivo', 4, 'mensual', NULL, NULL, '2024-10-03'), " +
                "(NULL, 30000.00, 'efectivo', 5, 'mensual', NULL, NULL, '2024-09-25'), " +
                "(NULL, 30000.00, 'efectivo', 6, 'mensual', NULL, NULL, '2024-09-25'), " +
                "(NULL, 11000.00, 'tarjeta', 7, 'diario', 2, 1, '2024-09-25'), " +
                "(NULL, 8500.00, 'tarjeta', 8, 'diario', 3, 3, '2024-09-25');"

        //      implementacion opcional a futuro
        private const val CREATE_TABLE_INSCRIPCION = "CREATE TABLE inscripcion" +
                "(id_inscripto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_cliente INTEGER NOT NULL," +
                "id_actividad INTEGER NOT NULL," +
                "FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)," +
                "FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad));"

        private const val INSERT_TABLE_INSCRIPCION = "INSERT INTO inscripcion VALUES " +
                "(NULL, 7, 1), " +
                "(NULL, 8, 3);"

    }
}
