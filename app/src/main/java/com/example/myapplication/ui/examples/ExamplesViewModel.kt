package com.example.myapplication.ui.examples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExamplesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is examples Fragment"
    }
    val text: LiveData<String> = _text
}