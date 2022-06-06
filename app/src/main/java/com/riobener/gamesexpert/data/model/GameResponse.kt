package com.riobener.gamesexpert.data.model

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("result")
    val results: List<Game>
)

data class Game(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("background_image")
    val background_image: String,
    @SerializedName("rating")
    val rating: Double
)