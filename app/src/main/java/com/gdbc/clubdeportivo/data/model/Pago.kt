package com.gdbc.clubdeportivo.data.model

import java.time.LocalDate

data class Pago(
	 val idPago:Int? ,
	 val monto:Double,
	 val metodoPago:String,
	 val idCliente:Int,
	 val tipoPago:String,
	 val idActividad:Int?,
	 val cantCuotas:Int?,
	 val fechaPago:LocalDate
)