package com.example.mobimarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
}