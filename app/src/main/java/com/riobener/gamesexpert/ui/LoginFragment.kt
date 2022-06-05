package com.riobener.gamesexpert.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.riobener.gamesexpert.databinding.FragmentLoginBinding
import com.riobener.gamesexpert.ui.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = mBinding.editTextUsername
        val password = mBinding.editTextPassword
        val loginButton = mBinding.loginButton
        loginButton.setOnClickListener{
            viewModel.authUser(username = username.text.toString(),password = password.text.toString())
            Toast.makeText(mBinding.root.context,"Successfully! ${viewModel.token}",Toast.LENGTH_LONG).show()
        }
    }
}