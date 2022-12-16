package com.example.hackathon2022.ui.dashboard

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon2022.api.PostData
import com.example.hackathon2022.api.ResponseData
import com.example.hackathon2022.repository.ApiRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private val apiRepository = ApiRepository.instance

    private val _postResponse = LiveEvent<Response<ResponseData.ResponseDriveLog>>()
    val postResponse: LiveData<Response<ResponseData.ResponseDriveLog>> get() = _postResponse

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

    fun postDriveLog(token: String, date: String, speed: Float, acceleration: Float, latitude: Float, longitude: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postData = PostData.DriveLogData(token, date, speed, acceleration, latitude, longitude)
                val response = apiRepository.postDriveLog(postData)
                _postResponse.postValue(response)
                if (response.isSuccessful) {
                    Log.d("postDriveSuccess", "${response}¥n${response.body()}")
                } else {
                    Log.d("postDriveFailure", "$response")
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.d("APItest", e.toString())
            }

        }
    }
}