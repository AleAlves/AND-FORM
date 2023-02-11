package com.example.dynamicformapp.feature.form.presentation.holder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import kotlin.math.min

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

    companion object {
        const val MASK_CHAR = '#'
    }

    // simple mutex
    private var isCursorRunning = false
    private var isDeleting = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    private fun applyMask(mask: String, onlyDigits: String): String {
        val maskPlaceholderCharCount = mask.count { it == MASK_CHAR }
        var maskCurrentCharIndex = 0
        var output = ""

        onlyDigits.take(min(maskPlaceholderCharCount, onlyDigits.length)).forEach { c ->
            for (i in maskCurrentCharIndex until mask.length) {
                if (mask[i] == MASK_CHAR) {
                    output += c
                    maskCurrentCharIndex += 1
                    break
                } else {
                    output += mask[i]
                    maskCurrentCharIndex = i + 1
                }
            }
        }
        return output
    }

    private fun removeMask(value: String): String {
        return Regex("\\D+").replace(value, "")
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        inputValue = s.toString()
        onInput(
            FormIO(
                position = adapterPosition, value = inputValue, isSelected = inputSelected
            )
        )
    }

    override fun afterTextChanged(s: Editable?) {
        if (isCursorRunning || isDeleting) {
            return
        }
        isCursorRunning = true

        if (mask.isNotEmpty()) {
            s?.let {
                val onlyDigits = removeMask(it.toString())
                it.clear()
                it.append(applyMask(mask, onlyDigits))
            }
        }
        isCursorRunning = false
    }

    override fun onCheckedChanged(view: CompoundButton?, isSelected: Boolean) {
        super.onCheckedChanged(view, isSelected)
        inputSelected = isSelected
        onInput(
            FormIO(
                position = adapterPosition, value = inputValue, isSelected = inputSelected
            )
        )
    }

    private fun onInput(io: FormIO) {
        onInput?.invoke(io)
    }
}