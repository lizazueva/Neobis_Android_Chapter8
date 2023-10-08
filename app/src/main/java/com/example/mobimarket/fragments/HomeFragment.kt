package com.example.mobimarket.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.adapter.AdapterProduct
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentHomeBinding
import com.example.mobimarket.model.Product
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.viewModel.MyProductsViewModel
import com.example.mobimarket.viewModel.ProductsListViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactoryMyProducts
import com.example.mobimarket.viewModel.ViewModelProviderFactoryProducts

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterProduct: AdapterProduct
    lateinit var viewModelProductsListFragment: ProductsListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val repository = Repository()
        val viewModelFactory = ViewModelProviderFactoryProducts(repository)
        viewModelProductsListFragment =
            ViewModelProvider( this, viewModelFactory).get(ProductsListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter()
        showProducts()
    }

    private fun showProducts() {
        viewModelProductsListFragment.getProducts()
        viewModelProductsListFragment.products.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { products ->
                        binding.imageBox.isVisible = products.isEmpty()
                        adapterProduct.differ.submitList(products)
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(requireContext(), "Не удалось загрузить товары", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }

    private fun adapter() {
        adapterProduct = AdapterProduct()
        binding.recyclerMenu.adapter = adapterProduct
        binding.recyclerMenu.layoutManager = GridLayoutManager(requireContext(), 2)

        adapterProduct.setOnItemClick(object : AdapterProduct.ListClickListener<Product> {
            override fun onClick(data: Product, position: Int) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data)
                findNavController().navigate(action)
            }

            override fun onDotsClick(data: Product, position: Int) {

            }

            override fun onLikeClick(data: Product, position: Int) {
            }

        })
    }
}