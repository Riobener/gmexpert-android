package com.riobener.gamesexpert.data.api

import com.riobener.gamesexpert.models.UserDataRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface GameService {
    @POST("/user/auth")
    suspend fun authUser(@Body userDataRequest: UserDataRequest)

    @POST("/user/register")
    suspend fun createUser(@Body userDataRequest: UserDataRequest)
}