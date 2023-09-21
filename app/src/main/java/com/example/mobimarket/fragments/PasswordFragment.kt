package com.example.mobimarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mobimarket.R
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentPasswordBinding
import com.example.mobimarket.viewModel.LoginViewModel
import com.example.mobimarket.viewModel.PasswordViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactory

class PasswordFragment : Fragment() {

    private lateinit var binding: FragmentPasswordBinding
    lateinit var viewModelPasswordFragment: PasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root

        val repository = Repository ()
        val viewModelFactory = ViewModelProviderFactory(repository)
        viewModelPasswordFragment = ViewModelProvider(this, viewModelFactory).get(PasswordViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}