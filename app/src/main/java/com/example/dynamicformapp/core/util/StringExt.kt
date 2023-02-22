package com.example.dynamicformapp.core.util

import android.text.Editable

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun String.clearMask() = Regex("\\D+").replace(this, "")