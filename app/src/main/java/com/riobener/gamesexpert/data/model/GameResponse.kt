package com.riobener.gamesexpert.data.model

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("results")
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

data class GameDetails(
    val id: Int,
    val name: String,
    val description: String,
    val released: String,
    val background_image: String,
    val rating: Double,
    val metacritic: Int,
    val website: String,
    val metacritic_url: String
)

data class GameScreenshots(
    val results: List<Screenshot>
)

data class Screenshot(
    val image: String
)

data class GameDetailsResponse(
    val details: GameDetails,
    val images: GameScreenshots,
    val inFavorite: Boolean
)

data class FavoriteResponse(
    val gameId: String,
    val gameTitle: String,
    val screenshot: String
)