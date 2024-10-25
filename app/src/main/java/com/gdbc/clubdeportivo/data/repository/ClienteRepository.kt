package com.gdbc.clubdeportivo.data.repository

import android.database.Cursor
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Cliente
import com.gdbc.clubdeportivo.data.util.ListadorDeClases
import java.time.LocalDate

class ClienteRepository(dbHelper:BDatos) {
	 private val db = dbHelper

	 fun clientePorDni(dni:String):Cliente? {
			val db = db.readableDatabase
			var cursor:Cursor? = null
			try {
				 val query = "SELECT * FROM Cliente WHERE dni = ?;"
				 cursor = db.rawQuery(query, arrayOf(dni))
				 if(cursor.moveToFirst()) {
						return Cliente(
							 idCliente = cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")),
							 nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
							 apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
							 dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
							 tieneAptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow("tiene_apto_fisico")),
							 tipoCliente = cursor.getString(cursor.getColumnIndexOrThrow("tipo_cliente")),
							 fechaRegistro = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_registro")))
						)
				 }
				 return null
			}catch (e:Exception) {
				 println("Error durante el login: ${e.message}")
				 return null
			}finally {
						cursor?.close()
				 		db.close()
			}
	 }


	 fun clientes(): List<Cliente>? {
			val db = db.readableDatabase
			var cursor: Cursor? = null
			val listaClientes = mutableListOf<Cliente>();
			try {
				 val query = "SELECT * FROM Cliente;"
				 cursor = db.rawQuery(query, null)
				 return ListadorDeClases.listarClientes(cursor)
			} catch (e: Exception) {
				 println("Error durante el login: ${e.message}")
				 return null
			} finally {
				 cursor?.close()
				 db.close()
			}
	 }

	 fun clientes(tipoCliente:String):List<Cliente>? {
			val db = db.readableDatabase
			var cursor:Cursor? = null
			val listaClientes = mutableListOf<Cliente>();
			try {
				 val query = "SELECT * FROM Cliente WHERE tipo_cliente = ?;"
				 cursor = db.rawQuery(query, arrayOf(tipoCliente))
				 return ListadorDeClases.listarClientes(cursor)
			}catch (e:Exception) {
				 println("Error durante el login: ${e.message}")
					return null
			}finally {
				 cursor?.close()
				 db.close()
			}
	 }
}