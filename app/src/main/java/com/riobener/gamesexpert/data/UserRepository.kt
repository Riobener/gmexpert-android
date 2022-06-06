package com.riobener.gamesexpert.data

import com.riobener.gamesexpert.data.api.UserService
import com.riobener.gamesexpert.data.model.LoggedInUser
import com.riobener.gamesexpert.data.model.SimpleResponse
import com.riobener.gamesexpert.data.model.TokenResponse
import com.riobener.gamesexpert.data.model.UserDataRequest
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        //TODO userService.logout()
    }

    suspend fun login(username: String, password: String): Response<TokenResponse> {
        // handle login
        val result = userService.authUser(UserDataRequest(username,password))
        if (result.isSuccessful) {
            setLoggedInUser(LoggedInUser("randomUUID","displayName",result.body()!!.token))
        }
        return result
    }

    suspend fun register(username: String, password: String): Response<SimpleResponse> {
        return userService.createUser(UserDataRequest(username, password))
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}