package com.example.hackathon2022.ui.login

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

class LoginViewModel: ViewModel() {

    private val apiRepository = ApiRepository.instance

    private val _loginResponse = LiveEvent<Response<ResponseData.ResponseGetUser>>()
    val loginResponse: LiveData<Response<ResponseData.ResponseGetUser>> get() = _loginResponse
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    fun putToken(p0: String) {
        _token.value = p0
    }

    fun login(name: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postData = PostData.LoginData(username = name, password = pass)
                val response = apiRepository.postLogin(postData)
                _loginResponse.postValue(response)
                if (response.isSuccessful) {
                    Log.d("LoginSuccess", "${response}¥n${response.body()}")
                } else {
                    Log.d("LoginFailure", "$response")
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.d("APItest", e.toString())
            }
        }
    }
}