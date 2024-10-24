package com.gdbc.clubdeportivo.data.repository

import android.content.ContentValues
import android.database.Cursor
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Pago
import java.time.LocalDate

class PagoRepository(dbHelper:BDatos,clienteRepository: ClienteRepository) {
			private val db = dbHelper
	 		private val clienteRepo = clienteRepository

			fun crearPago(pago: Pago):Boolean {
				 val db = db.writableDatabase
				 try {
						val contenedor = ContentValues()
						contenedor.put("monto",pago.monto)
						contenedor.put("metodo_pago",pago.metodoPago)
						contenedor.put("id_cliente",pago.idCliente)
						contenedor.put("tipo_pago",pago.tipoPago)
						contenedor.put("id_actividad",pago.idActividad)
						contenedor.put("cant_cuotas",pago.cantCuotas)
						contenedor.put("fecha_pago",pago.fechaPago.toString())
						val result = db.insert("Pago",null,contenedor)
						return result != 1L
				 }catch (e:Exception) {
						return false
				 }finally {
				 			db.close()
				 }
			}

		fun buscarUltimoPago(dni:String):Pago? {
			 val cliente = clienteRepo.clientePorDni(dni) ?: return null
			 val db = db.readableDatabase
			 val query = "SELECT * FROM pago  ORDER BY fecha_pago DESC LIMIT 1"
			 var cursor:Cursor? = null
			 try {
					cursor = db.rawQuery(query, arrayOf(cliente.idCliente.toString()))
					if(cursor.moveToFirst()) {
						 return Pago(
								 idPago= cursor.getInt(cursor.getColumnIndexOrThrow("id_pago")),
												monto= cursor.getDouble(cursor.getColumnIndexOrThrow("monto")),
												metodoPago= cursor.getString(cursor.getColumnIndexOrThrow("metodo_pago")),
												idCliente= cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")),
												tipoPago= cursor.getString(cursor.getColumnIndexOrThrow("tipo_pago")),
												idActividad= cursor.getInt(cursor.getColumnIndexOrThrow("id_actividad")),
												cantCuotas= cursor.getInt(cursor.getColumnIndexOrThrow("cant_cuotas")),
												fechaPago= LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_pago"))),
						 )
					}
					return null
			 }catch (e:Exception) {
					return  null
			 }finally {
			 			cursor?.close()
			 }
		}

	 fun pruebaBuscarUltimoPagoPorCliente():List<Pago>? {
			val db = db.readableDatabase
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
			val listPago = mutableListOf<Pago>()
			var cursor:Cursor? = null
			try {
				 cursor = db.rawQuery(query, null)
				 while(cursor.moveToNext()) {
						val pago =  Pago(
							 idPago= cursor.getInt(cursor.getColumnIndexOrThrow("id_pago")),
							 monto= cursor.getDouble(cursor.getColumnIndexOrThrow("monto")),
							 metodoPago= cursor.getString(cursor.getColumnIndexOrThrow("metodo_pago")),
							 idCliente= cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")),
							 tipoPago= cursor.getString(cursor.getColumnIndexOrThrow("tipo_pago")),
							 idActividad= cursor.getInt(cursor.getColumnIndexOrThrow("id_actividad")),
							 cantCuotas= cursor.getInt(cursor.getColumnIndexOrThrow("cant_cuotas")),
							 fechaPago= LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_pago"))),
						)
						listPago.add(pago)
				 }
				 return listPago
			}catch (e:Exception) {
				 return  null
			}finally {
				 cursor?.close()
			}
	 }
}