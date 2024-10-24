package com.gdbc.clubdeportivo.data.model

data class Usuario(
    val idUsuario: Int? = null,
    val alias: String,
    val contrasena: String,
    val rol: String
)