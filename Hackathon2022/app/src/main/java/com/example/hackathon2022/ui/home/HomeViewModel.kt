package com.example.hackathon2022.ui.home

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.URI

class HomeViewModel : ViewModel() {

    private val _acceleration = MutableLiveData<FloatArray>()
    val acceleration: LiveData<FloatArray> = _acceleration

    fun putAcceleration(p0: FloatArray) {
        _acceleration.value = p0
    }

    private val _backgroundColor = MutableLiveData<Int>()
    val backgroundColor: LiveData<Int> = _backgroundColor

    fun putBackgroundColor(color: Int) {
        _backgroundColor.value = color
    }

    private val _speed = MutableLiveData<Float>()
    val speed: LiveData<Float> = _speed

    fun putSpeed(p0: Float) {
        _speed.value = p0
    }

    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> = _latitude
    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double> = _longitude

    fun putLocation(lat: Double, lon: Double) {
        _latitude.value = lat
        _longitude.value = lon
    }
}