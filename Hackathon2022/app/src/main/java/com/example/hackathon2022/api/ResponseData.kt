package com.example.hackathon2022.api

import com.squareup.moshi.Json

class ResponseData {
    data class ResponseGetUser(
        @Json(name = "token") val token: String
    )

    data class ResponseDriveLog(
        @Json(name = "speed") val speed: Float,
        @Json(name = "acceleration") val acceleration: Float,
        @Json(name = "latitude") val latitude: Float,
        @Json(name = "longitude") val longitude: Float
    )
}