package com.riobener.gamesexpert.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riobener.gamesexpert.data.UserRepository
import com.riobener.gamesexpert.data.model.TokenResponse
import com.riobener.gamesexpert.data.model.UserDataRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _token = MutableLiveData<TokenResponse>()
    val token: LiveData<TokenResponse>
        get() = _token

    fun authUser(username: String, password: String) = viewModelScope.launch {
        userRepository.login(username,password).let{
            if(it.isSuccessful){
                _token.postValue(it.body())
            }else{
                Log.d("loginData","Error when login: ${it.errorBody()}")
            }
        }
    }
}