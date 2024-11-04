package com.gdbc.clubdeportivo.data.repository

import android.content.ContentValues
import android.database.Cursor
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Pago
import com.gdbc.clubdeportivo.data.util.ListadorDeClases
import java.time.LocalDate

class PagoRepository(private val dbHelper: BDatos) {

    fun crearPago(pago: Pago): Long {
        val db = dbHelper.writableDatabase
        var result: Long = -1
        try {
            val contenedor = ContentValues().apply {
                put("monto", pago.monto)
                put("metodo_pago", pago.metodoPago)
                put("id_cliente", pago.idCliente)
                put("tipo_pago", pago.tipoPago)
                put("id_actividad", pago.idActividad)
                put("cant_cuotas", pago.cantCuotas)
                put("fecha_pago", LocalDate.now().toString())
            }

            result = db.insert("Pago", null, contenedor)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return result
    }

    fun obtenerUltimoPago(idCliente: Int): Pago? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM pago WHERE id_cliente = ? ORDER BY fecha_pago DESC LIMIT 1",
            arrayOf(idCliente.toString())
        )
        var pago: Pago? = null
        if (cursor.moveToFirst()) {

            val idPago = cursor.getInt(cursor.getColumnIndexOrThrow("id_pago"))
            val monto = cursor.getDouble(cursor.getColumnIndexOrThrow("monto"))
            val metodoPago = cursor.getString(cursor.getColumnIndexOrThrow("metodo_pago"))
            val tipoPago = cursor.getString(cursor.getColumnIndexOrThrow("tipo_pago"))
            val fechaPago =
                LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_pago")))

            pago = Pago(idPago, monto, metodoPago, idCliente, tipoPago, null, null, fechaPago)
        }
        cursor.close()
        db.close()
        return pago
    }

    fun buscarUltimoPagoPorCliente(): List<Pago>? {
        val db = dbHelper.readableDatabase
        val query = """
    SELECT p.*
    FROM pago p
    INNER JOIN (
        SELECT id_cliente, MAX(fecha_pago) as max_fecha
        FROM pago
        GROUP BY id_cliente
    ) as pagos_recientes
    ON p.id_cliente = pagos_recientes.id_cliente AND p.fecha_pago = pagos_recientes.max_fecha
    ORDER BY p.fecha_pago DESC
""".trimIndent()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            return ListadorDeClases.listarPagos(cursor)
        } catch (e: Exception) {
            return null
        } finally {
            cursor?.close()
            db.close()
        }
    }
}