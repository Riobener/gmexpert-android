package com.riobener.gamesexpert.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.riobener.gamesexpert.data.api.GameService
import com.riobener.gamesexpert.data.api.UserService
import com.riobener.gamesexpert.data.model.Game
import com.riobener.gamesexpert.ui.adapters.GamesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesRepository @Inject constructor(private val gameService: GameService) {

    fun getGames(): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { GamesPagingSource(gameService) }
        ).flow
    }

    suspend fun getGamesByQuery(query: String)=
        gameService.findGamesBySearch(query,"10")

    suspend fun getGameDetails(id: Int) =
        gameService.findGameDetails(id)

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}