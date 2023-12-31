package com.example.mobimarket.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobimarket.databinding.ItemProductBinding
import com.example.mobimarket.model.Product
import com.example.mobimarket.utils.SharedPreferencesHelper

class AdapterProduct (private val sharedPreferencesHelper: SharedPreferencesHelper) : RecyclerView.Adapter<AdapterProduct.ViewHolder>() {

//    private var onItemClickListener: ((Product)-> Unit)? = null
    var onItemClickListener: ListClickListener<Product>? = null

    fun setOnItemClick(listClickListener: ListClickListener<Product>) {
        this.onItemClickListener = listClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val products = differ.currentList[position]
        with(holder.binding) {
            Glide.with(imageProduct).load(products.image).into(imageProduct)
            textLike.text = products.like_count.toString()
            textName.text = products.title
            textPrice.text = products.price
            holder.binding.cardItem.setOnClickListener{
                onItemClickListener?.onClick(products, position)
            }
            holder.binding.iconThreeDots.setOnClickListener {
                onItemClickListener?.onDotsClick(products, position)
            }
            holder.binding.imageLike.setOnClickListener {
                onItemClickListener?.onLikeClick(products, position)
            }
            if (sharedPreferencesHelper.isProductLiked(products.id)) {
                holder.binding.imageLike.setColorFilter(Color.RED)
            } else {
                holder.binding.imageLike.setColorFilter(Color.GRAY)
            }
        }
    }

    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    interface ListClickListener<T> {
        fun onClick(data: T, position: Int)
        fun onDotsClick(data: T, position: Int)
        fun onLikeClick(data: T, position: Int)
    }


    //установка differ(можно использовать вместо отдельного класса)
    private val differCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem== newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    fun removeItem(position: Int) {
        val newList = ArrayList(differ.currentList)
        newList.removeAt(position)
        differ.submitList(newList)
    }

    fun updateItem(updatedProduct: Product) {
        val position = differ.currentList.indexOfFirst { it.id == updatedProduct.id }
        if (position != -1) {
            val newList = ArrayList(differ.currentList)
            newList[position] = updatedProduct
            differ.submitList(newList)
        }
    }

}