package com.gdbc.clubdeportivo.data.repository

import android.content.ContentValues
import android.database.Cursor
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Actividad
import com.gdbc.clubdeportivo.data.model.Cliente
import com.gdbc.clubdeportivo.data.model.Pago
import com.gdbc.clubdeportivo.data.util.ListadorDeClases

class ActividadRepository(private val dbHelper:BDatos) {
		private val clienteRepo = ClienteRepository(dbHelper)
	 	private val pagoRepo = PagoRepository(dbHelper)

	 fun listarActividades(): List<Actividad>? {
			val db = dbHelper.readableDatabase
			var cursor: Cursor? = null
			val query = "SELECT * FROM Actividad;"
			val listaActividades = mutableListOf<Actividad>();
			try {
				 cursor = db.rawQuery(query, null)
				 return ListadorDeClases.listarActividades(cursor)
			} catch (e: Exception) {
				 return null
			} finally {
				 cursor?.close()
			}

	 }

	 fun pagarIncribirActividad(pago: Pago): Pago? {
			val db = dbHelper.writableDatabase
			try {
				 if (!pagoRepo.crearPago(pago)) return null
				 val contenedor = ContentValues()
				 contenedor.put("id_cliente", pago.idCliente)
				 contenedor.put("id_actividad", pago.idActividad)
				 val respInscripcion = db.insert("inscripcion", null, contenedor)
				 if (respInscripcion != -1L) {
						return pago
				 } else {
						return null
				 }
			} catch (e: Exception) {
				 return null
			} finally {
				 db.close()
			}
	 }

	 fun listarClientesPorActividad(idActividad:Int):List<Cliente>? {
			val db = dbHelper.readableDatabase
			var cursor:Cursor? = null
			val query = "SELECT c.* FROM inscripcion i " +
							"INNER JOIN Cliente c ON  i.id_cliente = c.id_cliente AND i.id_actividad = ?"
			val listaClientes = mutableListOf<Cliente>()
			try {
						cursor = db.rawQuery(query, arrayOf(idActividad.toString()))
				 		return ListadorDeClases.listarClientes(cursor)
			}catch (e:Exception) {
				 println("Exception al listar clientes por actividad: ${e.message}")
				 return null
			}
	 }
}