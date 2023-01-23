package com.example.dynamicformapp.feature.form.presentation.holder

import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.dynamicformapp.core.util.toEditable
import com.example.dynamicformapp.databinding.InputTextViewBinding
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormTextVO
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.FormViewHolder
import com.example.dynamicformapp.feature.form.presentation.TextInputWatcher


class FormTextViewHolder(private val binding: InputTextViewBinding) : FormViewHolder(binding.root) {

    private val watcher = TextInputWatcher {
        onNewInput?.invoke(
            FormInput(
                position = currentPosition, value = it, isSelected = binding.inputCheckbox.isChecked
            )
        )
    }

    init {
        binding.inputCheckbox.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked != view.isChecked) {
                onNewInput?.invoke(
                    FormInput(
                        position = currentPosition,
                        value = binding.inputViewEditext.text.toString(),
                        isSelected = isChecked
                    )
                )
            }
        }
    }

    override fun setupView(data: FormVO?) {
        data as FormTextVO
        with(binding) {
            inputTextViewSubtitle.text = data.subtitle
            inputViewEditext.let { edit ->
                if (data.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    edit.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    edit.transformationMethod = null
                }
                edit.setRawInputType(data.inputType)
                edit.removeTextChangedListener(watcher)
                edit.text = data.text.toEditable()
                edit.setSelection(edit.text?.length ?: 0)
                edit.filters += InputFilter.LengthFilter(data.maxSize)
                edit.error = data.error
                edit.hint = data.hint
                edit.addTextChangedListener(watcher)
            }
            if (data.checkBox == null) {
                binding.inputCheckbox.visibility = GONE
            } else {
                binding.inputCheckbox.visibility = VISIBLE
                binding.inputCheckbox.isChecked = data.checkBox.isSelected
                binding.inputCheckbox.text = data.checkBox.text
            }
        }
    }
}