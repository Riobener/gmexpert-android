package com.riobener.gamesexpert.data.model

import com.google.gson.annotations.SerializedName

data class SimpleResponse(
    @SerializedName("result")
    val result: String
)