package com.gdbc.clubdeportivo.data.model

enum class TipoCliente {
    SOCIO,
    NO_SOCIO
}

data class Cliente(
    val idCliente: Int? = null,
    val nombre: String,
    val apellido: String,
    val dni: String,
    val tieneAptoFisico: Boolean = false,
    val tipoCliente: TipoCliente = TipoCliente.NO_SOCIO,
    val fechaRegistro: String = ""
)

