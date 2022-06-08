package com.riobener.gamesexpert.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.riobener.gamesexpert.R
import com.riobener.gamesexpert.databinding.FragmentLoginBinding
import com.riobener.gamesexpert.ui.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONException

import android.content.SharedPreferences
import android.util.Log


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = mBinding.editTextUsername
        val password = mBinding.editTextPassword
        val loginButton = mBinding.loginButton
        val needRegisterTextView = mBinding.needRegisterTextView
        needRegisterTextView.setOnClickListener{
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        loginButton.setOnClickListener {loginButtonView->
            viewModel.authUser(username = username.text.toString(), password = password.text.toString())
            viewModel.token.observe(this, Observer {token->
                if(token!=null){
                    saveToken(token.token)
                    loginButtonView.findNavController().navigate(R.id.action_loginFragment_to_gamesListFragment)
                }

            })
        }
    }
    fun saveToken(token: String){
        val prefs: SharedPreferences = activity!!.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val edit: SharedPreferences.Editor = prefs.edit()
        try {
            val saveToken: String = token
            edit.putString("token", saveToken)
            Log.i("Login", saveToken)
            edit.commit()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}