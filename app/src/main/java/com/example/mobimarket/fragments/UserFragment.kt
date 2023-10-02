package com.example.mobimarket.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.databinding.AlertDialogExitBinding
import com.example.mobimarket.databinding.FragmentUserBinding


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegistrationFull.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_editProfileFragment)
        }

        binding.imageEdit.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_editProfileFragment)
        }

        binding.imageMyProducts.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_productFragment)
        }

        binding.imageLogout.setOnClickListener {
            callDialog()
        }
    }

    private fun callDialog() {
        val dialogBinding = AlertDialogExitBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialogBinding.buttonNo.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.buttonExit.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_userFragment_to_splashScreenFragment2)
        }
    }
}