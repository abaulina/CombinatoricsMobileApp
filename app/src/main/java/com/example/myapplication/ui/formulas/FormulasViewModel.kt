package com.example.myapplication.ui.formulas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormulasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is formulas Fragment"
    }
    val text: LiveData<String> = _text
}