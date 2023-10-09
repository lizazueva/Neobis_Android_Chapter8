package com.example.mobimarket.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    fun isProductLiked(productId: Int): Boolean {
        return sharedPreferences.getBoolean(productId.toString(), false)
    }

    fun setProductLiked(productId: Int, liked: Boolean) {
        sharedPreferences.edit().putBoolean(productId.toString(), liked).apply()
    }
}