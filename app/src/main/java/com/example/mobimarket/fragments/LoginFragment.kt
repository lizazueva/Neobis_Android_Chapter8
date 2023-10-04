package com.example.mobimarket.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentLoginBinding
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.viewModel.LoginViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactoryLogin
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var viewModelLoginFragment: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val repository = Repository()
        val viewModelFactory = ViewModelProviderFactoryLogin(repository)
        viewModelLoginFragment = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInput()

        binding.textRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)

//для теста
//            findNavController().navigate(R.id.action_loginFragment_to_userFragment)
        }


    }

    private fun checkInput() {

        binding.editTextName.addTextChangedListener(inputText)
        binding.editTextPassword.addTextChangedListener(inputText)
        checkButton()

    }

    private fun checkButton() {
        binding.buttonEnter.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            viewModelLoginFragment.login(name, password)
            login()
        }
    }

    private fun login() {
        viewModelLoginFragment.loginResult.observe(viewLifecycleOwner) { loginResult ->
            when (loginResult) {
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_userFragment)
                }
                is Resource.Error -> {
                    snackBar()
                }
                is Resource.Loading -> {
                }
            }
        }
    }

    val inputText = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            var nameInput = binding.editTextName.text.toString().trim()
            var passwordInput = binding.editTextPassword.text.toString().trim()

            if (!nameInput.isEmpty() && !passwordInput.isEmpty()) {
                binding.buttonEnter.isEnabled = true
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }


    private fun snackBar() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        val snackbarLayout = snackbarView as Snackbar.SnackbarLayout

        val layoutParams = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP

        snackbarLayout.setBackgroundColor(Color.TRANSPARENT)

        val customSnackbarView = layoutInflater.inflate(R.layout.snackbar_layout, null)
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackbarView)

        snackbar.show()

    }
}