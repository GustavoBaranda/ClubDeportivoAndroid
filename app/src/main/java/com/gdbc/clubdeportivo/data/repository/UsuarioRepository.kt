package com.gdbc.clubdeportivo.data.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.model.Cliente
import com.gdbc.clubdeportivo.data.model.Usuario

class UsuarioRepository(dbHelper: BDatos) {
    private val db = dbHelper

    fun crearUsuarioCliente(usuario: Usuario, cliente: Cliente): Boolean {
        val db = db.writableDatabase

        try {
            db.beginTransaction()

            //guardar el usuario
            val contenedor1 = ContentValues().apply {
                put("alias", usuario.alias)
                put("contrasena", usuario.contrasena)
                put("rol", usuario.rol)
            }
            val usuarioId = db.insert("Usuario", null, contenedor1)
            if (usuarioId == -1L) return false

            //guardar el cliente
            val contenedor2 = ContentValues().apply {
                put("nombre", cliente.nombre)
                put("apellido", cliente.apellido)
                put("dni", cliente.dni)
                put("tiene_apto_fisico", cliente.tieneAptoFisico)
                put("tipo_cliente", cliente.tipoCliente)
                put("fecha_registro", cliente.fechaRegistro.toString())
                put("id_usuario", usuarioId)
            }
            val clienteId = db.insert("Cliente", null, contenedor2)
            if (clienteId == -1L) return false

            cliente.idCliente = clienteId.toInt()

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

    fun loguearse(alias: String, contrasena: String): Usuario? {
        val db = db.readableDatabase
        var cursor: Cursor? = null
        return try {
            val query = "SELECT * FROM Usuario WHERE alias = ? AND contrasena = ?;"
            cursor = db.rawQuery(query, arrayOf(alias, contrasena))

            if (cursor.moveToFirst()) {

                Usuario(
                    idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario")),
                    alias = cursor.getString(cursor.getColumnIndexOrThrow("alias")),
                    contrasena = cursor.getString(cursor.getColumnIndexOrThrow("contrasena")),
                    rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"))
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("db", "Error al consultar la db:", e)
            null
        } finally {
            cursor?.close()
            db.close()
        }
    }

    fun existeAlias(alias: String): Boolean {
        val db = db.readableDatabase
        var cursor: Cursor? = null
        return try {
            val query = "SELECT * FROM Usuario WHERE alias = ?;"
            cursor = db.rawQuery(query, arrayOf(alias))
            val exists = cursor.moveToFirst()
            exists
        } catch (e: Exception) {
            false
        } finally {
            cursor?.close()
            db.close()
        }
    }
}