package com.example.hackathon2022.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.hackathon2022.api.PostData.*
import com.example.hackathon2022.api.ResponseData.*
import retrofit2.http.GET
import retrofit2.http.Query


interface RestApi {
    @POST("/user/create")
    suspend fun postRegister(@Body postRegister: RegisterData): Response<ResponseGetUser>

    @POST("/login")
    suspend fun postLogin(@Body postLogin: LoginData): Response<ResponseGetUser>

    @POST("/drivelog")
    suspend fun postDriveLog(@Body postDriveLog: DriveLogData): Response<ResponseDriveLog>

    @GET("/drivelog")
    suspend fun getDriveLog(@Query("token") token: String, @Query("date") date: String): Response<ResponseDriveLog>
}