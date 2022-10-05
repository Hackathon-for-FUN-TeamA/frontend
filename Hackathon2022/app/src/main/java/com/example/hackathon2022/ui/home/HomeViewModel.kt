package com.example.hackathon2022.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}