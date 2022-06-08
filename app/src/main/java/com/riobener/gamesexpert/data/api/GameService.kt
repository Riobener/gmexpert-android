package com.riobener.gamesexpert.data.api

import com.riobener.gamesexpert.data.model.GameDetailsResponse
import com.riobener.gamesexpert.data.model.GameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

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

}