package com.gdbc.clubdeportivo.data.model

import java.time.LocalDate

data class Pago(
    val idPago: Int? = null,
    val monto: Double,
    val metodoPago: String,
    val idCliente: Int,
    val tipoPago: String,
    val idActividad: Int?,
    val cantCuotas: Int?,
    val fechaPago: LocalDate
) {
    override fun toString(): String {
        return "Pago(idPago=$idPago, monto=$monto, metodoPago='$metodoPago', idCliente=$idCliente, tipoPago='$tipoPago', idActividad=$idActividad, cantCuotas=$cantCuotas, fechaPago=$fechaPago)"
    }
}