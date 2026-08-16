package com.example.supercartapp.view.auth

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentRegistrationBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.repository.AuthRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hide
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.util.validateEmail
import com.example.supercartapp.util.validateFullName
import com.example.supercartapp.util.validatePassword
import com.example.supercartapp.util.validatePhoneNumber
import com.example.supercartapp.viewmodel.AuthViewModel
import kotlin.getValue

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var binding: FragmentRegistrationBinding

    private val authViewModel: AuthViewModel by viewModels {
        val repository = AuthRepositoryImpl(ApiClient.apiService)
        AuthViewModel.AuthViewModelFactory(repository)
    }

    private var showPassword = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
        setUpUiListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        authViewModel.registerState.observe(viewLifecycleOwner){ state->
            with(binding){
                when(state){
                    is UiState.Loading -> {
                        pbRegisterPage.hideRest(tvRegistrationMessage)
                    }
                    is UiState.Error -> {
                        tvRegistrationMessage.text = state.message
                        tvRegistrationMessage.hideRest(pbRegisterPage)
                    }
                    is UiState.Success -> {
                        tvRegistrationMessage.hide()
                        pbRegisterPage.hide()
                        Toast.makeText(requireContext(),state.data.message, Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun setUpUiListeners() {
        with(binding) {
            acBtnRegister.setOnClickListener {
                tvRegistrationMessage.text = ""
                val emailId = etEmailId.text.toString()
                val password = etPassword.text.toString()
                val fullName = etFullName.text.toString()
                val mobileNumber = etMobileNumber.text.toString()
                val registerMessage = StringBuilder()
                if (!emailId.validateEmail()) {
                    etEmailId.requestFocus()
                    registerMessage.append("Please Enter Valid Email.")
                }
                if (!password.validatePassword()) {
                    etPassword.requestFocus()
                    registerMessage.append(
                        "\nPlease Enter Valid Password\n " +
                                "Password must be at least 8 characters with at least 1 letter and 1 number."
                    )
                }
                if (!fullName.validateFullName()) {
                    etFullName.requestFocus()
                    registerMessage.append(
                        "\nPlease Enter Valid Full Name\n " +
                                "Full name must contain only letters and spaces."
                    )
                }
                if (!mobileNumber.validatePhoneNumber()) {
                    etMobileNumber.requestFocus()
                    registerMessage.append(
                        "\nPlease Enter Valid Phone Number\n " +
                                "Phone number must contain 10 digits."
                    )
                }
                if (registerMessage.isNotEmpty()) {
                    tvRegistrationMessage.text = registerMessage.toString()
                    tvRegistrationMessage.hideRest()
                } else {
                    authViewModel.registerUser(
                        email = emailId,
                        password = password,
                        mobileNumber = mobileNumber,
                        fullName = fullName
                    )
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

            tvExistingAccount.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}