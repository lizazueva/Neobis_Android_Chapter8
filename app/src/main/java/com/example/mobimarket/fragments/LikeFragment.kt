package com.example.mobimarket.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.adapter.AdapterProduct
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentLikeBinding
import com.example.mobimarket.databinding.FragmentProductBinding
import com.example.mobimarket.model.Product
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.utils.SharedPreferencesHelper
import com.example.mobimarket.viewModel.LikedProductViewModel
import com.example.mobimarket.viewModel.MyProductsViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactoryLikedProducts
import com.example.mobimarket.viewModel.ViewModelProviderFactoryMyProducts

class LikeFragment : Fragment() {

    private lateinit var binding: FragmentLikeBinding
    private lateinit var adapterLikeProduct: AdapterProduct
    lateinit var viewModelProductLikeFragment: LikedProductViewModel
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikeBinding.inflate(inflater, container, false)
        val repository = Repository()
        val viewModelFactory = ViewModelProviderFactoryLikedProducts(repository)
        viewModelProductLikeFragment =
            ViewModelProvider(this, viewModelFactory).get(LikedProductViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_likeFragment_to_userFragment)
        }

        adapter()
        showLikeProducts()

    }

    private fun showLikeProducts() {
        viewModelProductLikeFragment.getLikedProducts()
        viewModelProductLikeFragment.liked_products.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { products ->
                        binding.imageBox.isVisible = products.isEmpty()
                        adapterLikeProduct.differ.submitList(products)
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(ContentValues.TAG, "Error: $message")
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }

    private fun adapter() {
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        adapterLikeProduct = AdapterProduct(sharedPreferencesHelper)
        binding.recyclerMenu.adapter = adapterLikeProduct
        binding.recyclerMenu.layoutManager = GridLayoutManager(requireContext(), 2)

        adapterLikeProduct.setOnItemClick(object : AdapterProduct.ListClickListener<Product> {
            override fun onClick(data: Product, position: Int) {
                val action = LikeFragmentDirections.actionLikeFragmentToDetailFragment(data)
                findNavController().navigate(action)

            }

            override fun onDotsClick(data: Product, position: Int) {

            }

            override fun onLikeClick(data: Product, position: Int) {
            }

        })

    }


}