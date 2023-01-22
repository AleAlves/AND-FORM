package com.example.dynamicformapp.feature.form.presentation.holder

import android.content.Context
import android.text.InputFilter
import android.view.LayoutInflater
import com.example.dynamicformapp.core.toEditable
import com.example.dynamicformapp.databinding.InputTextViewBinding
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormTextVO
import com.example.dynamicformapp.feature.form.presentation.TextInputWatcher

class FormTextViewHolder(context: Context) : BaseFormViewHolderImpl(context), BaseFormViewHolder {

    private val layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

    private val watcher = TextInputWatcher {
        onNewInput?.invoke(FormInput(currentPosition, value = it))
    }

    private var binding = InputTextViewBinding.inflate(layoutInflater, this, true)

    init {
        binding.inputCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onNewInput?.invoke(FormInput(currentPosition, isSelected = isChecked))
        }
    }

    override fun setupView(data: Any?) {
        data as FormTextVO?
        with(binding) {
            inputTextViewSubtitle.text = data?.subtitle
            inputViewEditext.let { edit ->
                edit.removeTextChangedListener(watcher)
                edit.text = data?.inputText?.toEditable()
                edit.setSelection(edit.text?.length ?: 0)
                edit.filters += InputFilter.LengthFilter(data?.maxCharacters ?: 0)
                edit.error = data?.inputError
                edit.hint = data?.inputHint
                edit.addTextChangedListener(watcher)
            }
            if (data?.checkBox == null) {
                binding.inputCheckbox.visibility = GONE
            } else {
                binding.inputCheckbox.visibility = VISIBLE
                binding.inputCheckbox.isChecked = data?.checkBox.isSelected
            }
        }
    }
}