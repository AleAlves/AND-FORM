package com.example.dynamicformapp.core.util

import android.widget.CheckBox

fun CheckBox.onListener(onCheck : (Boolean) -> Unit) {
    this.setOnCheckedChangeListener(null)
    this.setOnCheckedChangeListener { view, isChecked ->
        if (isChecked != view.isChecked) {
            onCheck.invoke(isChecked)
        }
    }
}