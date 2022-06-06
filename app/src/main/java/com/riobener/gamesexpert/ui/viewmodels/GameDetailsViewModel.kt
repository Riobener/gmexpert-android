package com.riobener.gamesexpert.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riobener.gamesexpert.data.model.GameDetailsResponse
import com.riobener.gamesexpert.data.repository.GamesRepository
import com.riobener.gamesexpert.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(private val repository: GamesRepository) : ViewModel() {
    val gameLiveData: MutableLiveData<Resource<GameDetailsResponse>> = MutableLiveData()

    fun getGameDetails(id: Int) =
        viewModelScope.launch {
            gameLiveData.postValue(Resource.Loading())
            val response = repository.getGameDetails(id)
            if(response.isSuccessful){
                response.body().let{res->
                    gameLiveData.postValue(Resource.Success(res))
                }
            }else{
                gameLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
}