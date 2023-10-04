package com.example.mobimarket.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        binding.buttonEnter.setOnClickListener {
            var email = binding.editTextMail.text.toString().trim()
            var username = binding.editTextName.text.toString().trim()
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToPasswordFragment(email, username)
            findNavController().navigate(action)
        }


        checkInput()
    }

    private fun checkInput() {
        binding.editTextName.addTextChangedListener(inputText)
        binding.editTextMail.addTextChangedListener(inputText)

    }



    val inputText = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            var isEmailValid = false
            var isNameValid = false
            val buttonEnter = binding.buttonEnter

            val nameInput = binding.editTextName.text.toString().trim()
            val mailInput = binding.editTextMail.text.toString().trim()

            validateEmail(mailInput)
            validateName(nameInput)

            if (binding.textInputLayoutMail.helperText == null
                && binding.textInputLayoutName.helperText == null){
                isEmailValid = true
                isNameValid = true
            }

            buttonEnter.isEnabled = isEmailValid && isNameValid

        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    private fun validateEmail(mailInput: String) {

        var isMailEmpty = mailInput.isEmpty()
        var isMailMatches = mailInput.matches(Regex("[A-Z a-z@.]*"))
        var isMailContains = mailInput.contains('@')&&mailInput.contains('.')

        if (isMailEmpty){
            binding.textInputLayoutMail.helperText = "Заполните это поле"
        }else if(!isMailMatches){
            binding.textInputLayoutMail.helperText = "Присутствуют недопустимые символы"
        }else if (!isMailContains){
            binding.textInputLayoutMail.helperText = "Нет специальных символов @, ."
        }else{
            binding.textInputLayoutMail.helperText = null
        }
    }

    private fun validateName(nameInput: String) {

        val isNameEmpty = nameInput.isEmpty()
        val isNameMatches = nameInput.matches(Regex("[A-Za-z]*"))

        if (!isNameMatches) {
            binding.textInputLayoutName.helperText = "Присутствуют недопустимые символы"
        } else if (isNameEmpty) {
            binding.textInputLayoutName.helperText = "Заполните это поле"
        } else {
            binding.textInputLayoutName.helperText = null
        }
    }

}