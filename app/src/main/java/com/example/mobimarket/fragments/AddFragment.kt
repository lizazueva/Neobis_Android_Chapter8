package com.example.mobimarket.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.adapter.AdapterPhoto
import com.example.mobimarket.adapter.AdapterProduct
import com.example.mobimarket.api.Repository
import com.example.mobimarket.databinding.FragmentAddBinding
import com.example.mobimarket.model.Product
import com.example.mobimarket.viewModel.AddProductViewModel
import com.example.mobimarket.viewModel.UserViewModel
import com.example.mobimarket.viewModel.ViewModelProviderFactoryLogin
import com.google.android.material.snackbar.Snackbar
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private var PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private lateinit var adapterPhoto: AdapterPhoto
    var photos = listOf<Uri>()
    lateinit var viewModelAddFragment: AddProductViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.recyclerPhoto.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterPhoto = AdapterPhoto(photos)
        binding.recyclerPhoto.adapter = adapterPhoto
        val repository = Repository()
        val viewModelFactory =
            AddProductViewModel.ViewModelProviderFactoryAddProduct(repository, requireContext())
        viewModelAddFragment = ViewModelProvider(this, viewModelFactory).get(AddProductViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardAddPhoto.setOnClickListener {
            chooseImage()
        }
        binding.imageDone.setOnClickListener {
            addProduct()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun addProduct() {
        val image = photos
        val title = binding.editTextName.text.toString()
        val description = binding.editTextDescrip.text.toString()
        val more_info = binding.editTextDetail.text.toString()
        val price = binding.editTextPrice.text.toString()

            viewModelAddFragment.createProduct(image, title, price, description, more_info,
                onSuccess = {
                    findNavController().navigate(R.id.action_addFragment_to_productFragment)
                    snackBar()
                },
                onError = {
                    Toast.makeText(requireContext(), "Ошибка при добавлении продукта", Toast.LENGTH_SHORT).show()
                }
            )
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
                photos += selectedImageUri!!
                adapterPhoto.notifyDataSetChanged()
            adapterPhoto = AdapterPhoto(photos)
            binding.recyclerPhoto.adapter = adapterPhoto
            }
        }

    private fun snackBar() {
        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        val snackbarLayout = snackbarView as Snackbar.SnackbarLayout

        val layoutParams = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP

        snackbarLayout.setBackgroundColor(Color.TRANSPARENT)

        val customSnackbarView = layoutInflater.inflate(R.layout.add_product_snackbar, null)
        snackbarLayout.removeAllViews()
        snackbarLayout.addView(customSnackbarView)

        snackbar.show()

    }
    }