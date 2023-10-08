package com.example.mobimarket.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val more_info: String,
    val price: String,
    val image: String,
    val like_count: Int
): Parcelable
