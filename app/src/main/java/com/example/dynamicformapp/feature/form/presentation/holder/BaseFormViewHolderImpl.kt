package com.example.dynamicformapp.feature.form.presentation.holder

import android.content.Context
import android.widget.LinearLayout
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormVO

abstract class BaseFormViewHolder(context: Context) : LinearLayout(context) {

    protected abstract fun setupView(data: Any?)

    var onNewInput: ((FormInput) -> Unit)? = null

    var data: FormVO? = null
        set(value) {
            setupView(value as Any)
            field = value
        }

    var currentPosition = 0
}