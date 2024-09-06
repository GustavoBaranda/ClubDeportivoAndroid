package com.gdbc.clubdeportivo.ui.abonar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AbonarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Abonar"
    }
    val text: LiveData<String> = _text
}
