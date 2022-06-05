package com.riobener.gamesexpert.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riobener.gamesexpert.data.api.UserService
import com.riobener.gamesexpert.models.TokenResponse
import com.riobener.gamesexpert.models.UserDataRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _token = MutableLiveData<TokenResponse>()
    val token: LiveData<TokenResponse>
        get() = _token

    fun authUser(username: String, password: String) = viewModelScope.launch {
        userService.authUser(UserDataRequest(username,password)).let{
            if(it.isSuccessful){
                _token.postValue(it.body())
            }else{
                Log.d("loginData","Error when login: ${it.errorBody()}")
            }
        }
    }
}