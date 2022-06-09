package com.riobener.gamesexpert.data.api

import com.riobener.gamesexpert.data.model.SimpleResponse
import com.riobener.gamesexpert.data.model.TokenResponse
import com.riobener.gamesexpert.data.model.UserDataRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("user/auth")
    suspend fun authUser(@Body userDataRequest: UserDataRequest): Response<TokenResponse>

    @POST("user/register")
    suspend fun createUser(@Body userDataRequest: UserDataRequest): Response<SimpleResponse>

    @GET("hello")
    suspend fun hello(@Header("Authorization") authHeader: String):Response<SimpleResponse>
}