package com.example.mobimarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.DateMask
import com.example.mobimarket.PhoneMask
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentEditPhoneBinding

class EditPhoneFragment : Fragment() {

    private lateinit var binding: FragmentEditPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_editPhoneFragment_to_editProfileFragment)
        }

        binding.buttonFurther.setOnClickListener {
            findNavController().navigate(R.id.action_editPhoneFragment_to_codeFragment)
        }

        checkInput()
    }

    private fun checkInput() {
        val editTextPhone = binding.editTextPhone
        val phoneMask = PhoneMask(editTextPhone)
        editTextPhone.addTextChangedListener(phoneMask)

        editTextPhone.addTextChangedListener { s ->
            validatePhone(s.toString().trim())
            binding.buttonFurther.isEnabled = binding.editTextPhone.error == null
        }
    }

    private fun validatePhone(phoneInput: String) {
        val isDateEmpty = phoneInput.isEmpty()
        val isPhoneValid = phoneInput.matches(Regex("[+\\d]+"))

        if (isPhoneValid) {
            binding.editTextPhone.error = "Неверный формат даты"
        } else if (isDateEmpty) {
            binding.editTextPhone.error = "Заполните это поле"
        } else {
            binding.editTextPhone.error = null
        }
    }
}