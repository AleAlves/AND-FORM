package com.example.dynamicformapp.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FormModel(
    var name: String = "",
    var email: String = "",
    var password: String = ""
) : Parcelable