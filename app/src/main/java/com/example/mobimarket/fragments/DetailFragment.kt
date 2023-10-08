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
import com.bumptech.glide.Glide
import com.example.mobimarket.R
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentDetailBinding
import com.example.mobimarket.model.Product
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.viewModel.DetailViewModel
import com.example.mobimarket.viewModel.MyProductsViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactoryDetail
import com.example.mobimarket.viewModel.ViewModelProviderFactoryMyProducts

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    lateinit var viewModelDetailFragment: DetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val repository = Repository()
        val viewModelFactory = ViewModelProviderFactoryDetail(repository)
        viewModelDetailFragment =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        observe()


    }

    private fun observe() {
        val place = arguments?.getParcelable<Product>("product")
        val id_product = place?.id

        if (id_product != null) {
            viewModelDetailFragment.productDetail(id_product)
            viewModelDetailFragment.product.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { product ->
                            binding.textNameProduct.text= product.title
                            binding.textDetail.text = product.description
                            binding.textMoreInfo.text = product.more_info
                            binding.textPriceProduct.text = product.price
                            Glide.with(binding.imageProduct).load(product.image).into(binding.imageProduct)
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Toast.makeText(requireContext(), "Не удалось загрузить товар", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Loading -> {

                    }
                }
            })
        }




    }
}