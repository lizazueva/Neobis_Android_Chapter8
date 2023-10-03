package com.example.mobimarket.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentPasswordBinding
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.viewModel.RegistrationViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactoryRegistration

class PasswordFragment : Fragment() {

    private lateinit var binding: FragmentPasswordBinding
    lateinit var viewModelPasswordFragment: RegistrationViewModel
    private var isNextButtonClicked = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPasswordBinding.inflate(inflater, container, false)
        val repository = Repository ()
        val viewModelFactory = ViewModelProviderFactoryRegistration(repository)
        viewModelPasswordFragment = ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_passwordFragment_to_registrationFragment)
        }

        binding.imageEye.setOnClickListener {
            eye()
        }

        checkInput()
        сlickButtonFurther()

    }

    private fun observe() {
        viewModelPasswordFragment.userSaved.observe(viewLifecycleOwner, {userSaved->
            when(userSaved) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_passwordFragment_to_userFragment)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Пользователь не зарегестрирован", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                }
            }
        })
    }

    private fun eye() {
        val editTextPassword = binding.editTextPassword
        val editTextRepeatPassword = binding.editTextRepeatPassword
        if (editTextPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            && editTextRepeatPassword.inputType ==InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ) {
            editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editTextRepeatPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            editTextPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editTextRepeatPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

        }
        editTextPassword.requestFocus()
        editTextPassword.setSelection(editTextPassword.text?.length ?: 0)
        editTextRepeatPassword.requestFocus()
        editTextRepeatPassword.setSelection(editTextRepeatPassword.text?.length ?: 0)
    }

    private fun сlickButtonFurther() {
        val buttonFurther = binding.buttonFurther
        buttonFurther.setOnClickListener {
            if (!isNextButtonClicked) {
                binding.textInputLayoutRepeatPassword.isEnabled = true
                binding.editTextRepeatPassword.requestFocus()
                buttonFurther.isEnabled = false
                buttonFurther.text = "Готово"
                isNextButtonClicked = true
            } else {
                val password = binding.editTextPassword.text.toString().trim()
                val password2 = binding.editTextRepeatPassword.text.toString().trim()
                val email = arguments?.getString("email")!!
                val username = arguments?.getString("username")!!
                validatePasswordRepeat(password2)
                if (binding.textInputLayoutRepeatPassword.helperText == null) {
                    viewModelPasswordFragment.newUser(username, email, password, password2)
                    observe()
                }
            }
        }
    }

    private fun checkInput() {
        binding.editTextPassword.requestFocus()
        binding.editTextPassword.addTextChangedListener(inputText)
        binding.editTextRepeatPassword.addTextChangedListener(inputText)
    }

    val inputText = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            var isPasswordValid = false
            val buttonFurther = binding.buttonFurther

            val passwordInput = binding.editTextPassword.text.toString().trim()

            validatePassword(passwordInput)

            if(binding.textInputLayoutPassword.helperText == null){
                isPasswordValid = true
            }

                buttonFurther.isEnabled = isPasswordValid
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    private fun validatePasswordRepeat(passwordRepeatInput: String) {
        val isPasswordEmpty = passwordRepeatInput.isEmpty()
        val passwordInput = binding.editTextPassword.text.toString().trim()

        if (passwordRepeatInput != passwordInput) {
            binding.textInputLayoutRepeatPassword.helperText = "Пароли не совпадают"
            binding.textInputLayoutRepeatPassword.editText?.setTextColor(Color.RED)
            binding.textInputLayoutPassword.editText?.setTextColor(Color.RED)
            binding.textInputLayoutRepeatPassword.setErrorTextColor(ColorStateList.valueOf(Color.RED))
        } else if (isPasswordEmpty) {
            binding.textInputLayoutRepeatPassword.helperText = "Заполните это поле"
        } else {
            binding.textInputLayoutRepeatPassword.helperText = null
            binding.textInputLayoutRepeatPassword.editText?.setTextColor(Color.BLACK)
            binding.textInputLayoutPassword.editText?.setTextColor(Color.BLACK)
            binding.textInputLayoutRepeatPassword.setErrorTextColor(ColorStateList.valueOf(Color.BLACK))
            binding.buttonFurther.isEnabled =true
        }

    }

    private fun validatePassword(passwordInput: String) {
        val isPasswordEmpty = passwordInput.isEmpty()
        val containsUppercase = passwordInput.matches(Regex(".*[A-Z].*"))
        val containsLowercase = passwordInput.matches(Regex(".*[a-z].*"))
        val containsSpecialCharacter =
            passwordInput.matches(Regex(".*[!\"#\$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~].*"))
        val isPasswordLength = passwordInput.length in 8..15
        val isPasswordOneNumber = passwordInput.any { it.isDigit() }

        if (isPasswordEmpty){
            binding.textInputLayoutPassword.helperText = "Заполните это поле"
        }else if (!containsUppercase){
            binding.textInputLayoutPassword.helperText = "Используйте строчные буквы"
        }else if (!containsLowercase){
            binding.textInputLayoutPassword.helperText = "Используйте прописные буквы"
        }else if (!containsSpecialCharacter){
            binding.textInputLayoutPassword.helperText = "Используйте минимум один спецсимвол"
        }else if (!isPasswordOneNumber) {
            binding.textInputLayoutPassword.helperText = "Используйте минимум одну цифру"
        }else if (!isPasswordLength) {
            binding.textInputLayoutPassword.helperText = "Длина пароля минимум 8 символов, макс 15"
        }else{
            binding.textInputLayoutPassword.helperText = null
        }

    }
}