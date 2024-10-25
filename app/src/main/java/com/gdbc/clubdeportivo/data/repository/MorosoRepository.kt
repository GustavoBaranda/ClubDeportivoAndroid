package com.gdbc.clubdeportivo.data.repository

import android.content.ContentValues
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Moroso
import com.gdbc.clubdeportivo.data.model.Pago
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import com.gdbc.clubdeportivo.data.repository.PagoRepository

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

	 fun agregarMoroso(idCliente: Int): Boolean {
			val db = dbHelper.writableDatabase
			val contenedor = ContentValues()
			contenedor.put("id_cliente", idCliente)
			val response = db.insert("Moroso", null, contenedor)
			return response != -1L
	 }

	 fun agregarNuevosMorosos(pagoRepository: PagoRepository): List<Moroso>? {
			val pagos = pagoRepository.pruebaBuscarUltimoPagoPorCliente() ?: return null
			val nuevosMorosos = mutableListOf<Pago>()
			pagos.forEach { p ->
				 if (ChronoUnit.DAYS.between(p.fechaPago, LocalDate.now()) > 30) nuevosMorosos.add(p)
			}
			nuevosMorosos.forEach { p -> agregarMoroso(p.idCliente) }
			return listarMorosos()
	 }

	 fun eliminarMoroso(idCliente: Int):Boolean {
			val db = dbHelper.writableDatabase
			val response = db.delete("Cliente", "id_cliente = ?", arrayOf(idCliente.toString()))
			return response > 0
	 }
}