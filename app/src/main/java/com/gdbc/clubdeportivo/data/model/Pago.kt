package com.gdbc.clubdeportivo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Pago(
    val idPago: Int? = null,
    val monto: Double,
    val metodoPago: String,
    val idCliente: Int,
    val tipoPago: String,
    val idActividad: Int?,
    val cantCuotas: Int?,
    val fechaPago: LocalDate
) : Parcelable {
    override fun toString(): String {
        return "Pago(idPago=$idPago, monto=$monto, metodoPago='$metodoPago', idCliente=$idCliente, tipoPago='$tipoPago', idActividad=$idActividad, cantCuotas=$cantCuotas, fechaPago=$fechaPago)"
    }
}