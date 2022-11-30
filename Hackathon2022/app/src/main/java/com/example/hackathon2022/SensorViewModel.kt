package com.example.hackathon2022

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.*
import com.example.hackathon2022.model.Date
import com.example.hackathon2022.model.DateRoomDatabase
import com.example.hackathon2022.repository.DateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SensorViewModel(application: Application, private val repository: DateRepository): AndroidViewModel(application){
    private val _acceleration = MutableLiveData<FloatArray>()
    val acceleration: LiveData<FloatArray> = _acceleration

    fun putAcceleration(p0: FloatArray) {
        _acceleration.value = p0
    }

    private val _speed = MutableLiveData<Float>()
    val speed: LiveData<Float> = _speed

    fun putSpeed(p0: Float) {
        _speed.value = p0
    }

    private val _backgroundColor = MutableLiveData<Int>()
    val backgroundColor: LiveData<Int> = _backgroundColor

    fun putBackgroundColor(color: Int) {
        _backgroundColor.value = color
    }

    private val _map = MutableLiveData<Bitmap>()
    val map: LiveData<Bitmap> = _map

    fun putMap(p0: Bitmap) {
        _map.value = p0
    }

    val allDate: LiveData<List<Date>> = repository.allDates.asLiveData()

    fun insert(date: Date) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insert(date)
        }
    }
}

class SensorViewModelFactory(private val repository: DateRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SensorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SensorViewModel(application = Application(), repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}