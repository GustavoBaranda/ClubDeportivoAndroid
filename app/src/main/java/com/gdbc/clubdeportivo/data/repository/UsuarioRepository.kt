package com.gdbc.clubdeportivo.data.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Cliente
import com.gdbc.clubdeportivo.data.model.Usuario

class UsuarioRepository(dbHelper:BDatos) {
	 private  val db = dbHelper

	 fun crearUsuarioCliente(usuario: Usuario, cliente: Cliente): Boolean {

			val db = db.writableDatabase

			try {
				 //guardar el usuario
				 db.beginTransaction()
				 val contenedor1 = ContentValues();
				 contenedor1.put("alias", usuario.alias)
				 contenedor1.put("contrasena", usuario.contrasena)
				 contenedor1.put("rol", usuario.rol)
				 val usuarioId = db.insert("Usuario", null, contenedor1)
				 if (usuarioId == -1L) return false
				 //guardar el cliente
				 val contenedor2 = ContentValues();
				 contenedor2.put("nombre", cliente.nombre)
				 contenedor2.put("apellido", cliente.apellido)
				 contenedor2.put("dni", cliente.dni)
				 contenedor2.put("tiene_apto_fisico", cliente.tieneAptoFisico)
				 contenedor2.put("tipo_cliente", cliente.tipoCliente)
				 contenedor2.put("fecha_registro", cliente.fechaRegistro.toString())
				 contenedor2.put("id_usuario", usuarioId)
				 val resultado = db.insert("Cliente", null, contenedor2)
				 if (resultado == -1L) {
						return false
				 }
						db.setTransactionSuccessful()
						return true
			} catch (e: SQLiteConstraintException) {
				 e.printStackTrace() // Puedes registrar el error para depurar
				 return false
			} finally {
				 db.endTransaction() // Termina la transacción (si no fue exitosa, hace rollback)
				 db.close()
			}
	 }

	 fun loguearse(usuario: Usuario): Usuario? {
			val db = db.readableDatabase
			var cursor: Cursor? = null
			try {
				 val query = "SELECT * FROM Usuario WHERE alias = ? AND contrasena = ?;"
				 cursor = db.rawQuery(query, arrayOf(usuario.alias, usuario.contrasena))

				 if (cursor.moveToFirst()) {

						return Usuario(
							 alias = cursor.getString(cursor.getColumnIndexOrThrow("alias")),
							 contrasena = cursor.getString(cursor.getColumnIndexOrThrow("contrasena")),
							 rol = cursor.getString(cursor.getColumnIndexOrThrow("rol")),
							 idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario"))
						)
				 } else {
						println("No se encontró el usuario con el alias y la contraseña proporcionados")
						return null
				 }
			} catch (e: Exception) {

				 println("Error durante el login: ${e.message}")
				 return null
			} finally {

				 cursor?.close()
				 db.close()
			}

	 }
}