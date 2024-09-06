package com.gdbc.clubdeportivo.ui.listarcuotasvencidas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListarCuotasVencidasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Listar cuotas vencidas"
    }
    val text: LiveData<String> = _text
}
