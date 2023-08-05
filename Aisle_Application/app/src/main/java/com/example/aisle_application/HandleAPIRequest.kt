package com.example.aisle_application
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*
interface HandleAPIRequest {
    @POST("users/phone_number_login")
    suspend fun sendOTPToPhone(@Query("number") number: String): Response<JsonObject>

    @POST("users/verify_otp")
    suspend fun verifyOTP(@Query("number") number: String, @Query("otp") otp: String):  Response<JsonObject>

    @GET("users/test_profile_list")
    suspend fun getUserProfile(@Header("Authorization") token: String):  Response<JsonObject>
}