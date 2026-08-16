package com.example.supercartapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentLoginBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.repository.AuthRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hide
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.util.validateEmail
import com.example.supercartapp.util.validatePassword
import com.example.supercartapp.view.MainActivity
import com.example.supercartapp.viewmodel.AuthViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val authViewModel: AuthViewModel by viewModels {
        val repository = AuthRepositoryImpl(ApiClient.apiService)
        AuthViewModel.AuthViewModelFactory(repository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        setUpUiListeners()
        setUpObserver()
    }
    private var showPassword = false

    private fun setUpUiListeners() {
        with(binding) {
            acbtnLoginUser.setOnClickListener {
                tvLoginMessage.text = ""
                val emailId = etEmail.text.toString()
                val password = etPassword.text.toString()
                val loginMessage = StringBuilder()
                if (!emailId.validateEmail()) {
                    etEmail.requestFocus()
                    loginMessage.append("Please Enter Valid Email.")
                }
                if (!password.validatePassword()) {
                    etPassword.requestFocus()
                    loginMessage.append(
                        "\nPlease Enter Valid Password\n " +
                                "Password must be at least 8 characters with at least 1 letter and 1 number."
                    )
                }
                if (loginMessage.isNotEmpty()) {
                    tvLoginMessage.text = loginMessage.toString()
                    tvLoginMessage.hideRest()
                } else {
                    authViewModel.loginUser(email = emailId, password = password)
                }
            }
            imgBtnTogglePassword.setOnClickListener {
                showPassword = !showPassword
                if (showPassword) {
                    imgBtnTogglePassword.setImageResource(R.drawable.show_password_icon)
                    etPassword.transformationMethod = null
                } else {
                    imgBtnTogglePassword.setImageResource(R.drawable.hide_password_icon)
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
                etPassword.setSelection(etPassword.text.toString().length)
            }

            tvNewUser.setOnClickListener {
                findNavController().navigate(R.id.action_login_fragment_to_register_fragment)
            }
        }
    }

    private fun setUpObserver(){
        with(binding){
            authViewModel.loginState.observe(viewLifecycleOwner){state->
                when(state){
                    is UiState.Loading -> {
                        pbLoginPage.hideRest(tvLoginMessage)
                    }
                    is UiState.Error -> {
                        tvLoginMessage.text = state.message
                        tvLoginMessage.hideRest(pbLoginPage)
                    }
                    is UiState.Success ->{
                        pbLoginPage.hide()
                        tvLoginMessage.hide()
//                        findNavController().navigate(R.id.nav_main){
//                            popUpTo(R.id.nav_auth){
//                                inclusive = true
//                            }
//                        }
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
        }
    }

}