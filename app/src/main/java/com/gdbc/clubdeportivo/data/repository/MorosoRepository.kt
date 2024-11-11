package com.gdbc.clubdeportivo.data.repository

import android.content.ContentValues
import android.util.Log
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Moroso
import com.gdbc.clubdeportivo.data.model.Pago
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class MorosoRepository(private val dbHelper: BDatos) {

    fun listarMorosos(): MutableList<Moroso> {
        val db = dbHelper.readableDatabase
        val list: MutableList<Moroso> = ArrayList()
        val query = "SELECT c.id_cliente, c.nombre, c.apellido,c.dni " +
                "FROM moroso m JOIN Cliente c " +
                "ON m.id_cliente = c.id_cliente;"
        val cursor = db.rawQuery(query, null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val moroso = Moroso(
                        idCliente = cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                        dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                    )
                    list.add(moroso)
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

    fun esMoroso(idCliente: Int): Boolean {
        val db = dbHelper.readableDatabase
        val query = "SELECT COUNT(*) FROM moroso WHERE id_cliente = ?"
        val cursor = db.rawQuery(query, arrayOf(idCliente.toString()))

        val esMoroso = cursor.use {
            it.moveToFirst() && it.getInt(0) > 0
        }
        cursor.close()
        db.close()
        return esMoroso
    }

    fun agregarMoroso(idCliente: Int): Boolean {
        val db = dbHelper.writableDatabase
        val contenedor = ContentValues()
        contenedor.put("id_cliente", idCliente)

        db.beginTransaction()
        return try {
            val response = db.insert("Moroso", null, contenedor)
            if (response != -1L) {
                db.setTransactionSuccessful()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("dbase", "Error al agregar moroso: ${e.message}")
            false
        } finally {
            db.endTransaction()
        }
    }

    fun agregarNuevosMorosos(pagoRepository: PagoRepository) {
        val pagos = pagoRepository.buscarUltimoPagoPorCliente().orEmpty()
        val nuevosMorosos = mutableListOf<Pago>()
        pagos.forEach { p ->
            if (!esMoroso(p.idCliente)) {
                if (p.tipoPago == "mensual" && ChronoUnit.DAYS.between(
                        p.fechaPago,
                        LocalDate.now()
                    ) > 30
                ) nuevosMorosos.add(p)
                else if (p.tipoPago == "diario" && ChronoUnit.DAYS.between(
                        p.fechaPago,
                        LocalDate.now()
                    ) > 1
                ) nuevosMorosos.add(p)
            }
        }
        nuevosMorosos.forEach { p -> agregarMoroso(p.idCliente) }
    }

    fun eliminarMoroso(idCliente: Int): Boolean {
        val db = dbHelper.writableDatabase
        val response = db.delete("Moroso", "id_cliente = ?", arrayOf(idCliente.toString()))
        return response > 0
    }
}