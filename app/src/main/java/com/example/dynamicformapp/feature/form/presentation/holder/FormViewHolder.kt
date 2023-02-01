package com.example.dynamicformapp.feature.form.presentation.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.feature.form.domain.model.FormData
import com.example.dynamicformapp.feature.form.domain.model.FormVO

abstract class FormViewHolder<T>(
    val view: View
) : RecyclerView.ViewHolder(view) {

    protected abstract fun setupView(data: T?)

    var onNewInput: ((FormData) -> Unit)? = null

    var data: T? = null
        set(value) {
            setupView(value)
            field = value
        }

    var currentPosition = 0
        set(value) {
            field = value
        }
}