package com.riobener.gamesexpert.ui.adapters

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.riobener.gamesexpert.data.api.GameService
import com.riobener.gamesexpert.data.model.Game
import com.riobener.gamesexpert.data.model.GameResponse
import com.riobener.gamesexpert.data.repository.GamesRepository
import com.riobener.gamesexpert.utils.Resource
import javax.inject.Inject

class GamesPagingSource @Inject constructor(private val service: GameService, private val token: String) :
    PagingSource<Int, Game>() {
    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val response = service.findGames(page.toString(), "10", token)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
