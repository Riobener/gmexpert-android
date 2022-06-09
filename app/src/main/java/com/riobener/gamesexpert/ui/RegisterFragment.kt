package com.riobener.gamesexpert.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.riobener.gamesexpert.R
import com.riobener.gamesexpert.databinding.FragmentLoginBinding
import com.riobener.gamesexpert.databinding.FragmentRegisterBinding
import com.riobener.gamesexpert.ui.viewmodels.LoginViewModel
import com.riobener.gamesexpert.ui.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = mBinding.editTextUsername
        val password = mBinding.editTextPassword
        val repeatPassword = mBinding.editTextRepeatPassword
        val registerButton = mBinding.registerButton
        val needLoginTextView = mBinding.needToLoginTextView
        needLoginTextView.setOnClickListener{view->
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        registerButton.setOnClickListener {registerButtonView->
            if(repeatPassword.text.toString()==password.text.toString()){
                viewModel.registerUser(username = username.text.toString(), password = password.text.toString())
                viewModel.result.observe(this, Observer {
                    if(it.result=="OK"){
                        findNavController().safeNavigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                        Toast.makeText(mBinding.root.context,"Аккаунт успешно создан!",Toast.LENGTH_SHORT).show()
                    } else if(it.result=="NOT OK"){
                        Toast.makeText(mBinding.root.context,"Такой пользователь уже существует!",Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                Toast.makeText(mBinding.root.context,"Пароли не совпадают!",Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun NavController.safeNavigate(direction: NavDirections) {
        Log.d("CLICK", "Click happened")
        currentDestination?.getAction(direction.actionId)?.run {
            Log.d("CLICK", "Click Propagated")
            navigate(direction)
        }
    }
}