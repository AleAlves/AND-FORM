package com.example.dynamicformapp.core.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox

fun CheckBox.onListener(onCheck: (Boolean) -> Unit) {
    this.setOnCheckedChangeListener(null)
    this.setOnCheckedChangeListener { view, isChecked ->
        if (isChecked != view.isChecked) {
            onCheck.invoke(isChecked)
        }
    }
}

fun Context?.showKeyboard(view: View?) {
    val im: InputMethodManager? =
        this?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    im?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context?.hideKeyboard(view: View?) {
    val im: InputMethodManager? =
        this?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    im?.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY)
}