package com.riobener.gamesexpert.ui.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riobener.gamesexpert.data.model.SimpleResponse
import com.riobener.gamesexpert.data.repository.UserRepository
import com.riobener.gamesexpert.data.model.TokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _token = MutableLiveData<TokenResponse>()
    val token: LiveData<TokenResponse>
        get() = _token

    fun authUser(username: String, password: String, context: Context) = viewModelScope.launch {
        userRepository.login(username,password).let{
            if(it.isSuccessful){
                _token.postValue(it.body())
            }else{
                Toast.makeText(context, "Неверные данные!", Toast.LENGTH_SHORT).show()
                Log.d("loginData","Error when login: ${it.errorBody()}")
            }
        }
    }

    private val _result = MutableLiveData<SimpleResponse>()
    val result: LiveData<SimpleResponse>
        get() = _result

    fun hello(token: String) = viewModelScope.launch {
        userRepository.hello(token).let{
            if(it.isSuccessful){
                _result.postValue(it.body())
            }else{
                Log.d("loginData","Error when login: ${it.errorBody()}")
            }
        }
    }
}