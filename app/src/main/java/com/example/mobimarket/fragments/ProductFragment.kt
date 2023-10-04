package com.example.mobimarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobimarket.R
import com.example.mobimarket.adapter.AdapterProduct
import com.example.mobimarket.databinding.FragmentProductBinding
import com.example.mobimarket.model.Product


class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var adapterProduct: AdapterProduct
    var products = emptyList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}