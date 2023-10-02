package com.example.mobimarket.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobimarket.databinding.ItemImageBinding

class AdapterPhoto (private val photos: List<Uri>) : RecyclerView.Adapter<AdapterPhoto.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoUri = photos[position]
        with(holder.binding) {
            Glide.with(imageProduct).load(photoUri).into(imageProduct)
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }
    inner class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

}