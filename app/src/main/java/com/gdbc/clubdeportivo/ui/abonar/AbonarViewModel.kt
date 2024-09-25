package com.gdbc.clubdeportivo.ui.abonar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AbonarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun setDni(dni: String) {
        _text.value = "Abonar al cliente con DNI: $dni"
    }
}
