package com.gdbc.clubdeportivo.ui.abonar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AbonarViewModel : ViewModel() {

    private val _dni = MutableLiveData<String>()
    val dni: LiveData<String> get()  = _dni

    fun setDni(dni: String) {
        _dni.value = dni
    }
}
