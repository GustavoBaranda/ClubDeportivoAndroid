package com.gdbc.clubdeportivo.data.util

import android.database.Cursor
import com.gdbc.clubdeportivo.data.model.Pago
import java.time.LocalDate

class ListadorDeClases {
    companion object {

        fun listarPagos(cursor: Cursor): List<Pago>? {
            val listaPagos = mutableListOf<Pago>()
            try {
                while (cursor.moveToNext()) {
                    val pago = Pago(
                        idPago = cursor.getInt(cursor.getColumnIndexOrThrow("id_pago")),
                        monto = cursor.getDouble(cursor.getColumnIndexOrThrow("monto")),
                        metodoPago = cursor.getString(cursor.getColumnIndexOrThrow("metodo_pago")),
                        idCliente = cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")),
                        tipoPago = cursor.getString(cursor.getColumnIndexOrThrow("tipo_pago")),
                        idActividad = cursor.getInt(cursor.getColumnIndexOrThrow("id_actividad")),
                        cantCuotas = cursor.getInt(cursor.getColumnIndexOrThrow("cant_cuotas")),
                        fechaPago = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_pago"))),
                    )
                    listaPagos.add(pago)
                }
                return listaPagos
            } catch (e: Exception) {
                return null
            }
        }
    }
}