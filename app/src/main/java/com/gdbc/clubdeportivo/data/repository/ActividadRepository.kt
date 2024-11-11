package com.gdbc.clubdeportivo.data.repository

import com.gdbc.clubdeportivo.data.database.BDatos

class ActividadRepository(private val dbHelper: BDatos) {


    fun obtenerPrecioPorNombre(nombre: String): Double? {
        val db = dbHelper.readableDatabase
        var precio: Double? = null
        val cursor = db.rawQuery(
            "SELECT precio_diario FROM actividad WHERE nombre_actividad = ?",
            arrayOf(nombre)
        )
        if (cursor.moveToFirst()) {
            precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio_diario"))
        }
        cursor.close()
        return precio
    }

    fun obtenerIdPorNombre(nombre: String): Int? {
        val db = dbHelper.readableDatabase
        var id: Int? = null
        val cursor = db.rawQuery(
            "SELECT id_actividad FROM actividad WHERE nombre_actividad = ?",
            arrayOf(nombre)
        )
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id_actividad"))
        }
        cursor.close()
        return id
    }
}