package com.example.dynamicformapp.feature.form.presentation.holder

import android.R.attr.editable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
    protected var mask: String = ""

    var data: T? = null
        set(value) {
            setupView(value)
            field = value
        }

    private var isRunning = false
    private var isDeleting = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.d("WOW", "onTextChanged:    $s")
        inputValue = s.toString()
        onInput(
            FormIO(
                position = position, value = inputValue, isSelected = inputSelected
            )
        )
    }

    override fun afterTextChanged(editable: Editable?) {
        if (isRunning || isDeleting) {
            return
        }
        isRunning = true

        val editableLength: Int = editable?.length ?: 0
        if (editableLength < mask.length) {
            if (mask[editableLength] != '#') {
                editable?.append(mask[editableLength])
            } else if (mask[editableLength - 1] != '#') {
                editable?.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }
        }
        isRunning = false
    }

    override fun onCheckedChanged(view: CompoundButton?, isSelected: Boolean) {
        inputSelected = isSelected
        onInput(
            FormIO(
                position = position, value = inputValue, isSelected = inputSelected
            )
        )
    }

    private fun onInput(io: FormIO) {
        onInput?.invoke(io)
    }
}