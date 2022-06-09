package com.riobener.gamesexpert.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.riobener.gamesexpert.R

class LogoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteToken()
        view.findNavController().navigate(R.id.action_logoutFragment_to_loginFragment)
    }

    private fun deleteToken(){
        val prefs: SharedPreferences = activity!!.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        prefs.edit().remove("token").apply()
    }
}