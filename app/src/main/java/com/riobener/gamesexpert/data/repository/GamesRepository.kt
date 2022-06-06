package com.riobener.gamesexpert.data.repository

import com.riobener.gamesexpert.data.api.GameService
import com.riobener.gamesexpert.data.api.UserService
import javax.inject.Inject

class GamesRepository @Inject constructor(private val gameService: GameService) {
    suspend fun getGames(page: String, page_size: String) =
        gameService.findGames(page, page_size)
}