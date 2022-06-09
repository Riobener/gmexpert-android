package com.riobener.gamesexpert.data.repository

import com.riobener.gamesexpert.data.api.UserService
import com.riobener.gamesexpert.data.model.SimpleResponse
import com.riobener.gamesexpert.data.model.TokenResponse
import com.riobener.gamesexpert.data.model.UserDataRequest
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService) {


    suspend fun login(username: String, password: String): Response<TokenResponse> {
        return userService.authUser(UserDataRequest(username, password))
    }

    suspend fun register(username: String, password: String): Response<SimpleResponse> {
        return userService.createUser(UserDataRequest(username, password))
    }

    suspend fun hello(token: String): Response<SimpleResponse> {
        return userService.hello(token)
    }

}