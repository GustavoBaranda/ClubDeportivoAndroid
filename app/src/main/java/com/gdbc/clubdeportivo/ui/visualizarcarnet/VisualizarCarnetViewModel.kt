package com.gdbc.clubdeportivo.ui.visualizarcarnet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VisualizarCarnetViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "A LABURAR LOS FRONTSSSSSSS"
    }
    val text: LiveData<String> = _text
}