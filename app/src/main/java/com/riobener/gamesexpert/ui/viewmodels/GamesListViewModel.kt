package com.riobener.gamesexpert.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.riobener.gamesexpert.data.model.FavoriteResponse
import com.riobener.gamesexpert.data.model.Game
import com.riobener.gamesexpert.data.model.GameResponse
import com.riobener.gamesexpert.data.repository.GamesRepository
import com.riobener.gamesexpert.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesListViewModel @Inject constructor(private val repository: GamesRepository) : ViewModel() {
    fun getGames(token: String): Flow<PagingData<Game>> {
        return repository.getGames(token)
    }

    val favoritesLiveData: MutableLiveData<Resource<List<FavoriteResponse>>> = MutableLiveData()

    fun getFavorites(token:String) =
        viewModelScope.launch {
            favoritesLiveData.postValue(Resource.Loading())
            val response = repository.getFavorites(token)
            if (response.isSuccessful) {
                response.body().let { res ->
                    favoritesLiveData.postValue(Resource.Success(res))
                }
            } else {
                favoritesLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
}