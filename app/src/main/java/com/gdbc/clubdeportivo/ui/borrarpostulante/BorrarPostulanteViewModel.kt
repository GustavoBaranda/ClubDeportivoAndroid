package com.gdbc.clubdeportivo.ui.borrarpostulante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BorrarPostulanteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Borrar Postulante"
    }
    val text: LiveData<String> = _text
}