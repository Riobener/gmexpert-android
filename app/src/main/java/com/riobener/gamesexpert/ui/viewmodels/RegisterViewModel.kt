package com.riobener.gamesexpert.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riobener.gamesexpert.data.UserRepository
import com.riobener.gamesexpert.data.model.SimpleResponse
import com.riobener.gamesexpert.data.model.TokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _result = MutableLiveData<SimpleResponse>()
    val result: LiveData<SimpleResponse>
        get() = _result

    fun registerUser(username: String, password: String) = viewModelScope.launch {
        userRepository.register(username,password).let{
            if(it.isSuccessful){
                _result.postValue(it.body())
            }else{
                Log.d("loginData","Error when login: ${it.errorBody()}")
            }
        }
    }
}