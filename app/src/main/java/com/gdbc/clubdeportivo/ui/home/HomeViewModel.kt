package com.gdbc.clubdeportivo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "¿Quienes somos?\n" +
                "\n" +
                "Somos el grupo 2 Comisión D\n" +
                "DAM\n" +
                "\n" +
                "Alejandro Abadi\n" +
                "Gustavo Baranda\n" +
                "Ma. Eugenia Bava\n" +
                "Catriel Escobar\n" +
                "Marcelo Galimberti"
    }
    val text: LiveData<String> = _text
}