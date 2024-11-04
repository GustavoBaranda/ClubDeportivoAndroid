package com.gdbc.clubdeportivo.ui.comprobante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ComprobanteModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Finalizar Inscripcion"
    }
    val text: LiveData<String> = _text
}