package com.example.dynamicformapp.feature.form.presentation.holder

import android.content.Context
import android.widget.LinearLayout
import com.example.dynamicformapp.feature.form.model.FormVO

abstract class BaseFormViewHolder(context: Context) : LinearLayout(context) {

    protected abstract fun setupView(data: Any?)

    var onTextInput: ((Int, String) -> Unit)? = null
    var onCheckInput: ((Int, Boolean) -> Unit)? = null

    var data: FormVO? = null
        set(value) {
            setupView(value as Any)
            field = value
        }

    var currentPosition = 0
}