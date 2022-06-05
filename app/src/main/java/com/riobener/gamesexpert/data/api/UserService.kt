package com.riobener.gamesexpert.data.api

import com.riobener.gamesexpert.models.TokenResponse
import com.riobener.gamesexpert.models.UserDataRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/user/auth")
    suspend fun authUser(@Body userDataRequest: UserDataRequest): Response<TokenResponse>

    @POST("/user/register")
    suspend fun createUser(@Body userDataRequest: UserDataRequest)
}