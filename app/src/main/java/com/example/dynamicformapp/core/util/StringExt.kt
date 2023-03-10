package com.example.dynamicformapp.core.util

import android.text.Editable

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun String.clearMask() = Regex("[^0-9A-z ]+").replace(this, "")

fun String.applyMask(mask: String): String {
    val builder = StringBuilder(this)
    mask.mapIndexed { index, char ->
        if (index <= this.length && char.toString() != "#") {
            builder.insert(index, char)
        }
    }
    return builder.toString()
}

fun String.getMaskCharacterCount() = this.count { it != '#' }

fun String.getPlainCharacterCount() = this.count { it.isDigit() || it.isLetter() }