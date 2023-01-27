package com.example.dynamicformapp.feature.form.presentation.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.TextInputWatcher

abstract class FormViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view) {

    protected abstract fun setupView(data: FormVO?)

    var onNewInput: ((FormInput) -> Unit)? = null

    var data: FormVO? = null
        set(value) {
            setupView(value)
            field = value
        }

    var currentPosition = 0
        set(value) {
            field = value
        }
}