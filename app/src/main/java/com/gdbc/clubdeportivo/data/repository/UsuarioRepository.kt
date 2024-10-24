package com.gdbc.clubdeportivo.data.repository

import android.content.ContentValues
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Cliente
import com.gdbc.clubdeportivo.data.model.Usuario

class UsuarioRepository(dbHelper:BDatos) {
	 private  val db = dbHelper

	 fun crearUsuarioCliente(usuario: Usuario, cliente: Cliente): Boolean {

			val guardar = db.writableDatabase

			try {
				 //guardar el usuario
				 guardar.beginTransaction()
				 val contenedor1 = ContentValues();
				 contenedor1.put("alias", usuario.alias)
				 contenedor1.put("contrasena", usuario.contrasena)
				 contenedor1.put("rol", usuario.rol)
				 val usuarioId = guardar.insert("Usuario", null, contenedor1)
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
				 val resultado = guardar.insert("Cliente", null, contenedor2)
				 if (resultado != 1L) {
						guardar.setTransactionSuccessful()
				 }
				 return true

			} catch (e: Exception) {
				 println("Error durante la transacción: ${e.message}")
				 return false
			} finally {
				 guardar.endTransaction() // Termina la transacción (si no fue exitosa, hace rollback)
				 guardar.close()
			}
	 }

	 fun loguearse(usuario: Usuario):Usuario {
			
	 }

}