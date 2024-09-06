package com.gdbc.clubdeportivo.ui.inscripciondeactividades

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IncripcionDeActividadesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ingresar Cliente"
    }
    val text: LiveData<String> = _text
}
