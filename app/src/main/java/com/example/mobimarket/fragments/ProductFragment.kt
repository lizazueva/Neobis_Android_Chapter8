package com.example.mobimarket.fragments

import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.adapter.AdapterProduct
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.AlertDialogDeleteBinding
import com.example.mobimarket.databinding.BottomSheetDialogBinding
import com.example.mobimarket.databinding.FragmentProductBinding
import com.example.mobimarket.model.Product
import com.example.mobimarket.utils.Resource
import com.example.mobimarket.utils.SharedPreferencesHelper
import com.example.mobimarket.viewModel.MyProductsViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactoryMyProducts
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar


class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var adapterProduct: AdapterProduct
    lateinit var viewModelProductFragment: MyProductsViewModel
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        val repository = Repository()
        val viewModelFactory = ViewModelProviderFactoryMyProducts(repository, requireContext())
        viewModelProductFragment =
            ViewModelProvider(this, viewModelFactory).get(MyProductsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_productFragment_to_userFragment)
        }

        adapter()
        showMyProducts()


    }

    private fun showMyProducts() {
        viewModelProductFragment.getMyProducts()
        viewModelProductFragment.my_products.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { products ->
                        binding.imageBox.isVisible = products.isEmpty()
                        adapterProduct.differ.submitList(products)
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "Error: $message")
                    }
                }

                is Resource.Loading -> {

                }
            }
        })
    }

    private fun adapter() {
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        adapterProduct = AdapterProduct(sharedPreferencesHelper)
        binding.recyclerMenu.adapter = adapterProduct
        binding.recyclerMenu.layoutManager = GridLayoutManager(requireContext(), 2)

        adapterProduct.setOnItemClick(object : AdapterProduct.ListClickListener<Product> {
            override fun onClick(data: Product, position: Int) {
                val action = ProductFragmentDirections.actionProductFragmentToDetailFragment(data)
                findNavController().navigate(action)

            }

            override fun onDotsClick(data: Product, position: Int) {
                dialog(data, position)
            }

            override fun onLikeClick(data: Product, position: Int) {
            }

        })

    }

    fun dialog(data: Product, position: Int) {
        val binding: BottomSheetDialogBinding =
            BottomSheetDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)
        binding.layoutEdit.setOnClickListener {
            val action = ProductFragmentDirections.actionProductFragmentToAddFragment(data)
            findNavController().navigate(action)
            dialog.dismiss()
        }
        binding.layoutDelete.setOnClickListener {
            alert_dialog_menu(data, position)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun alert_dialog_menu(data: Product, position: Int) {
        val dialogBinding = AlertDialogDeleteBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialogBinding.buttonNo.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.buttonDelete.setOnClickListener {
            dialog.dismiss()
            productDelete(data, position)
        }

    }

    fun productDelete(data: Product, position: Int) {
        viewModelProductFragment.productDelete(
            onSuccess = {
                adapterProduct.removeItem(position)
                snackBar()
            },
            onError = {
                Toast.makeText(requireContext(), "Ошибка удаления товара", Toast.LENGTH_SHORT).show()
            },
            data.id
        )
    }

    private fun snackBar() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        val snackbarLayout = snackbarView as Snackbar.SnackbarLayout

        val layoutParams = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP

        snackbarLayout.setBackgroundColor(Color.TRANSPARENT)

        val customSnackbarView = layoutInflater.inflate(R.layout.delete_product_snackbar, null)
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackbarView)

        snackbar.show()

    }
}