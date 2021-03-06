package com.riobener.gamesexpert.data.model

import com.google.gson.annotations.SerializedName

data class UserDataRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)