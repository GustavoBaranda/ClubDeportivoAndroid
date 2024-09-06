package com.gdbc.clubdeportivo.ui.ingresarcliente

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IngresarClienteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ingresar Cliente"
    }
    val text: LiveData<String> = _text
}
