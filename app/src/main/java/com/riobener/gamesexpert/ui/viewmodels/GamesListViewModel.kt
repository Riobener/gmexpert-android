package com.riobener.gamesexpert.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.riobener.gamesexpert.data.api.GameService
import com.riobener.gamesexpert.data.model.Game
import com.riobener.gamesexpert.data.model.GameResponse
import com.riobener.gamesexpert.data.model.TokenResponse
import com.riobener.gamesexpert.data.repository.GamesRepository
import com.riobener.gamesexpert.ui.adapters.GamesPagingSource
import com.riobener.gamesexpert.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesListViewModel @Inject constructor(private val repository: GamesRepository) : ViewModel() {
    val games = repository.getGames()
}