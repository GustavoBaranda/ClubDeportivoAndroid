package com.gdbc.clubdeportivo.data.util

import android.database.Cursor
import com.gdbc.clubdeportivo.data.model.Actividad
import com.gdbc.clubdeportivo.data.model.Cliente
import com.gdbc.clubdeportivo.data.model.Pago
import java.time.LocalDate

class ListadorDeClases {
	 companion object {
			fun listarClientes (cursor: Cursor):List<Cliente>? {

				 val listaClientes = mutableListOf<Cliente>()
				 try {
						while (cursor.moveToNext()) {
							 val cliente = Cliente(
									idCliente = cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")),
									nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
									apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
									dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
									tieneAptoFisico = cursor.getInt(cursor.getColumnIndexOrThrow("tiene_apto_fisico")),
									tipoCliente = cursor.getString(cursor.getColumnIndexOrThrow("tipo_cliente")),
									fechaRegistro = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fecha_registro")))
							 )
							 listaClientes.add(cliente)
						}
						return listaClientes
				 } catch (e: Exception) {
						return null
				 }
			}

			fun listarActividades(cursor: Cursor):List<Actividad>? {
				 val listaActividades = mutableListOf<Actividad>();
				 try {
						while (cursor.moveToNext()) {
							 listaActividades.add(
									Actividad(
										 idActividad = cursor.getInt(cursor.getColumnIndexOrThrow("id_actividad")),
										 nombreActividad = cursor.getString(cursor.getColumnIndexOrThrow("nombre_actividad")),
										 precioDiario = cursor.getDouble(cursor.getColumnIndexOrThrow("precio_diario"))
									)
							 )
						}
						return listaActividades
				 }catch (e:Exception) {
						return null
				 }
			}

			fun listarPagos(cursor: Cursor):List<Pago>? {
				 val listaPagos = mutableListOf<Pago>()
				 try {
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
							 listaPagos.add(pago)
						}
						return listaPagos
				 }catch (e:Exception) {
						return null
				 }
			}

	 }
}