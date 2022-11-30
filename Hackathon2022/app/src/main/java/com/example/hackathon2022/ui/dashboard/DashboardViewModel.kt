package com.example.hackathon2022.ui.dashboard

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _map = MutableLiveData<Bitmap>()
    val map: LiveData<Bitmap> = _map

    fun putMap(p0: Bitmap) {
        _map.value = p0
    }

    private val _dateList = MutableLiveData<List<String>>()
    val dateList: LiveData<List<String>> = _dateList

    var dataListSize = 0

    fun putDateList(p0: List<String>) {
        _dateList.value = p0
    }
}