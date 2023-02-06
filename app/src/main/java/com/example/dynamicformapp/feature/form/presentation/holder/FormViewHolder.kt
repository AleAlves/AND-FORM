package com.example.dynamicformapp.feature.form.presentation.holder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.feature.form.domain.model.FormIO

interface InputListeners : TextWatcher, CompoundButton.OnCheckedChangeListener {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun afterTextChanged(p0: Editable?) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun onCheckedChanged(view: CompoundButton?, isSelected: Boolean) {}
}

abstract class FormViewHolder<T>(
    val view: View
) : RecyclerView.ViewHolder(view), InputListeners {

    protected abstract fun setupView(data: T?)

    var onInput: ((FormIO) -> Unit)? = null

    protected var inputValue = ""
    protected var inputSelected = false

    var data: T? = null
        set(value) {
            setupView(value)
            field = value
        }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        inputValue = s.toString()
        onInput(
            FormIO(
                position = adapterPosition,
                value = inputValue,
                isSelected = inputSelected
            )
        )
    }

    override fun onCheckedChanged(view: CompoundButton?, isSelected: Boolean) {
        super.onCheckedChanged(view, isSelected)
        inputSelected = isSelected
        onInput(
            FormIO(
                position = adapterPosition,
                value = inputValue,
                isSelected = inputSelected
            )
        )
    }

    private fun onInput(io: FormIO) {
        onInput?.invoke(io)
    }
}