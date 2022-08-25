package com.example.offline_crypto.ui.addcoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddcoinViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is add coin Fragment"
    }
    val text: LiveData<String> = _text
}