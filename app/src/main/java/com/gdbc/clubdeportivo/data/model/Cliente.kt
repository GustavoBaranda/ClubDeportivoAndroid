package com.gdbc.clubdeportivo.data.model

import java.time.LocalDate

data class Cliente(
    var idCliente: Int? = null,
    val nombre: String,
    val apellido: String,
    val dni: String,
    val tieneAptoFisico: Int = 0,
    val tipoCliente: String,
    val fechaRegistro: LocalDate = LocalDate.now()
)

