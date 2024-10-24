package com.gdbc.clubdeportivo.ui.borrarpostulante

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BorrarPostulanteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Postulantes borrados"
    }
    val text: LiveData<String> = _text
}