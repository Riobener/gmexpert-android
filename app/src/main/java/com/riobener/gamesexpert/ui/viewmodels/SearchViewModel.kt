package com.riobener.gamesexpert.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riobener.gamesexpert.data.model.GameResponse
import com.riobener.gamesexpert.data.repository.GamesRepository
import com.riobener.gamesexpert.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: GamesRepository): ViewModel() {

    val searchGamesLiveData: MutableLiveData<Resource<GameResponse>> = MutableLiveData()

    fun getSearchGames(query: String, token:String) =
        viewModelScope.launch {
            searchGamesLiveData.postValue(Resource.Loading())
            val response = repository.getGamesByQuery(query = query,token)
            if (response.isSuccessful) {
                response.body().let { res ->
                    searchGamesLiveData.postValue(Resource.Success(res))
                }
            } else {
                searchGamesLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
}