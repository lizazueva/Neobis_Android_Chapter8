package com.example.mobimarket.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobimarket.DateMask
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentEditProfileBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private var PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPhone.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_editPhoneFragment)
        }

        binding.imageCancel.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_userFragment)
        }

        binding.imageDone.setOnClickListener {
            //сохранение данных
            findNavController().navigate(R.id.action_editProfileFragment_to_userFragment)
        }

        binding.textPhoto.setOnClickListener {
            chooseImage()
        }

        checkInput()

    }

    private fun checkInput() {

        binding.editTextName.addTextChangedListener(inputText)
        binding.editTextSurname.addTextChangedListener(inputText)
        binding.editTextLogin.addTextChangedListener(inputText)
        val editTextDate = binding.editTextDate
        val dateMask = DateMask(editTextDate)
        editTextDate.addTextChangedListener(dateMask)

    }


    val inputText = object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            var isLoginValid = false
            var isNameValid = false
            var isSurnameValid = false
            var isDateValid = false


            val nameInput = binding.editTextName.text.toString().trim()
            val loginInput = binding.editTextLogin.text.toString().trim()
            val surnameInput = binding.editTextSurname.text.toString().trim()
            val dateInput = binding.editTextDate.text.toString().trim()

            val buttonDone = binding.imageDone

            validateName(nameInput)
            validateLogin(loginInput)
            validateSurname(surnameInput)
            validateDate(dateInput)

                if (binding.editTextDate.error == null && binding.editTextSurname.error == null
                    && binding.editTextLogin.error == null && binding.editTextName.error == null
                ) {
                    isLoginValid = true
                    isNameValid = true
                    isSurnameValid = true
                    isDateValid = true
                }

                buttonDone.isClickable =
                    isDateValid && isNameValid && isSurnameValid && isLoginValid

        }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

    private fun validateDate(dateInput: String) {
        val isDateEmpty = dateInput.isEmpty()
        val isDateMatches = dateInput.matches(Regex("\\\\d{2}\\\\.\\\\d{2}\\\\.\\\\d{4}"))

        if (isDateMatches) {
            binding.editTextDate.error = "Неверный формат даты"
        } else if (isDateEmpty) {
            binding.editTextDate.error = "Заполните это поле"
        } else {
            binding.editTextDate.error = null
        }
    }

    private fun validateSurname(surnameInput: String) {
        val isSurnameEmpty = surnameInput.isEmpty()
        val isSurnameMatches = surnameInput.matches(Regex("[A-Za-zА-Яа-я]*"))

        if (!isSurnameMatches) {
            binding.editTextSurname.error = "Присутствуют недопустимые символы"
        } else if (isSurnameEmpty) {
            binding.editTextSurname.error = "Заполните это поле"
        } else {
            binding.editTextSurname.error = null
        }

    }

    private fun validateLogin(loginInput: String) {
        val isLoginEmpty = loginInput.isEmpty()
        val isLoginMatches = loginInput.matches(Regex("[A-Za-z]*"))

        if (!isLoginMatches) {
            binding.editTextLogin.error = "Присутствуют недопустимые символы"
        } else if (isLoginEmpty) {
            binding.editTextLogin.error = "Заполните это поле"
        } else {
            binding.editTextLogin.error = null
        }

    }

    private fun validateName(nameInput: String) {
        val isNameEmpty = nameInput.isEmpty()
        val isNameMatches = nameInput.matches(Regex("[A-Za-zА-Яа-я]*"))

        if (!isNameMatches) {
            binding.editTextName.error = "Присутствуют недопустимые символы"
        } else if (isNameEmpty) {
            binding.editTextName.error = "Заполните это поле"
        } else {
            binding.editTextName.error = null
        }

    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            Glide.with(this).load(selectedImageUri).into(binding.imageProfile)
        }
    }
}