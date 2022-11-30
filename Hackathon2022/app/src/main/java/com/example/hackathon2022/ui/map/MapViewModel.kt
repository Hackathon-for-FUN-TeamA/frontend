package com.example.hackathon2022.ui.map

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    private val _map = MutableLiveData<Bitmap>()
    val map: LiveData<Bitmap> = _map

    fun putMap(p0: Bitmap) {
        _map.value = p0
    }
}