package com.riobener.gamesexpert.ui.viewmodels

import android.util.Log
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
class GamesListViewModel @Inject constructor(private val repository: GamesRepository) : ViewModel() {
    val gamesLiveData: MutableLiveData<Resource<GameResponse>> = MutableLiveData()
    var pageNumber = 1
    var pageSize = 10

    init{
        getGames()
    }

    private fun getGames() =
        viewModelScope.launch {
            gamesLiveData.postValue(Resource.Loading())
            val response = repository.getGames(pageNumber.toString(), pageSize.toString())
            if(response.isSuccessful){
                response.body().let{res->
                    gamesLiveData.postValue(Resource.Success(res))
                }
            }else{
                gamesLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
}