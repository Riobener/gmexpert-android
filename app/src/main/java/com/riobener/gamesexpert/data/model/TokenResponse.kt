package com.riobener.gamesexpert.data.model

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("jwt")
    val token: String
)