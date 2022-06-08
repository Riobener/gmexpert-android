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

class GamesRepository @Inject constructor(private val gameService: GameService, ) {

    fun getGames(token: String): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { GamesPagingSource(gameService,token) }
        ).flow
    }

    suspend fun getGamesByQuery(query: String, token: String)=
        gameService.findGamesBySearch(query,NETWORK_PAGE_SIZE.toString(), token)

    suspend fun getGameDetails(id: Int, token: String) =
        gameService.findGameDetails(id, token)

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}