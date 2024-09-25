package com.gdbc.clubdeportivo.data.repository

import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Cliente

class MorosoRepository(private val dbHelper: BDatos) {

    fun listarMorosos(): MutableList<Cliente> {
        val db = dbHelper.readableDatabase
        val list: MutableList<Cliente> = ArrayList()
        val query = "SELECT c.nombre, c.apellido,c.dni " +
                "FROM moroso m JOIN Cliente c " +
                "ON m.id_cliente = c.id_cliente;"
        val cursor = db.rawQuery(query, null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val cliente = Cliente(
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                        dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                    )
                    list.add(cliente)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }
        return list
    }
}