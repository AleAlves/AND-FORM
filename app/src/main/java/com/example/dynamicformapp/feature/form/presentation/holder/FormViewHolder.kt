package com.example.dynamicformapp.feature.form.presentation.holder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.feature.form.domain.model.FormIO

abstract class FormViewHolder<T>(
    val view: View
) : RecyclerView.ViewHolder(view), TextWatcher {

    protected abstract fun setupView(data: T?)
    abstract fun onTextChange(value: String)

    var onInput: ((FormIO) -> Unit)? = null

    var data: T? = null
        set(value) {
            setupView(value)
            field = value
        }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onTextChange(s.toString())
    }
}