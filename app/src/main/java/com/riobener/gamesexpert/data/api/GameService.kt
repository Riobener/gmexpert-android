package com.riobener.gamesexpert.data.api

import com.riobener.gamesexpert.data.model.GameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GameService {
    @GET("games")
    suspend fun findGames(
        @Query(value = "page") page: String,
        @Query(value = "page_size") page_size: String
    ): Response<GameResponse>
}