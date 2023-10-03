package com.example.mobimarket.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegistrationRequest (
    val username: String,
    val email: String,
    val password: String,
    val password2: String
):Parcelable