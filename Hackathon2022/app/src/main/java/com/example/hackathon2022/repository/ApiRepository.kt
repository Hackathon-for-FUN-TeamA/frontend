package com.example.hackathon2022.repository

import com.example.hackathon2022.api.Params.Companion.BASE_URL
import com.example.hackathon2022.api.PostData
import com.example.hackathon2022.api.ResponseData
import com.example.hackathon2022.api.RestApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiRepository {
    // 10秒でタイムアウトとなるように設定
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val service: RestApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
        .create(RestApi::class.java)

    suspend fun postRegister(postData: PostData.RegisterData): Response<ResponseData.ResponseGetUser> =
        withContext(IO){service.postRegister(postData)}

    suspend fun postLogin(postData: PostData.LoginData): Response<ResponseData.ResponseGetUser> =
        withContext(IO){service.postLogin(postData)}

    suspend fun postDriveLog(postData: PostData.DriveLogData): Response<ResponseData.ResponseDriveLog> =
        withContext(IO){service.postDriveLog(postData)}

    companion object Factory {
        val instance: ApiRepository
        @Synchronized get() {
            return ApiRepository()
        }
    }
}