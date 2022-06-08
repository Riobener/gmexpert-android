package com.riobener.gamesexpert.data.api

import com.riobener.gamesexpert.data.model.FavoriteResponse
import com.riobener.gamesexpert.data.model.GameDetailsResponse
import com.riobener.gamesexpert.data.model.GameResponse
import retrofit2.Response
import retrofit2.http.*

interface GameService {
    @GET("games")
    suspend fun findGames(
        @Query(value = "page") page: String,
        @Query(value = "page_size") page_size: String,
        @Header("Authorization") authHeader: String
    ): GameResponse

    @GET("games/search")
    suspend fun findGamesBySearch(
        @Query(value = "query") query: String,
        @Query(value = "page_size") page_size: String,
        @Header("Authorization") authHeader: String
    ): Response<GameResponse>

    @GET("games/details")
    suspend fun findGameDetails(
        @Query(value = "gameId") id: Int,
        @Header("Authorization") authHeader: String
    ): Response<GameDetailsResponse>

    @POST("games/favorite")
    suspend fun addFavorite(
        @Query (value = "gameId") gameId: String,
        @Query (value = "title") title: String,
        @Query (value = "screenshot") screenshot: String,
        @Header("Authorization") authHeader: String
    ): Response<FavoriteResponse>

    @DELETE("games/favorite")
    suspend fun deleteFavorite(
        @Query (value = "gameId") gameId: String,
        @Header("Authorization") authHeader: String
    ): Response<Int>

    @GET("games/favorite")
    suspend fun getFavorites(
        @Header("Authorization") authHeader: String
    ): Response<List<FavoriteResponse>>

}