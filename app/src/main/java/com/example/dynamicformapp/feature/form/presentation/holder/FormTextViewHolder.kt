package com.example.dynamicformapp.feature.form.presentation.holder

import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import com.example.dynamicformapp.core.util.getMaskCharacterCount
import com.example.dynamicformapp.core.util.getPlainCharacterCount
import com.example.dynamicformapp.databinding.InputTextViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO


class FormTextViewHolder<T>(
    private val binding: InputTextViewBinding
) : FormViewHolder<T>(binding.root) {

    override fun setupView(data: T?) {
        data as FormTextVO

        mask = data.mask
        inputValue = data.text
        inputSelected = data.checkBox?.isSelected ?: false

        binding.root.layoutParams = LinearLayout.LayoutParams(
            if (data.fill) LinearLayout.LayoutParams.MATCH_PARENT else LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        with(binding) {
            inputTextViewSubtitle.text = data.helper
            inputViewEditext.let { edit ->
                edit.removeTextChangedListener(this@FormTextViewHolder)
                if (data.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    edit.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    edit.transformationMethod = null
                }
                edit.setRawInputType(data.inputType)
                edit.filters = arrayOf(InputFilter.LengthFilter(data.maxSize))
                edit.setText(inputValue)
                edit.setSelection(edit.text?.length ?: 0)
                edit.error = data.error
                edit.hint = data.hint
                inputTextViewCounter.text = "${data.text.getPlainCharacterCount() }/${data.maxSize - mask.getMaskCharacterCount()}"
                if (data.isReadOnly) {
                    edit.isEnabled = false
                    inputTextViewCounter.visibility = GONE
                    if (data.helper.isEmpty()) {
                        inputTextViewSubtitle.visibility = GONE
                    }
                } else {
                    edit.isEnabled = data.isEnabled
                }
                with(data.requestFocus) {
                    if (this) {
                        edit.requestFocus()
                    }
                }
                edit.addTextChangedListener(this@FormTextViewHolder)
            }

            with(binding.inputCheckbox) {
                setOnCheckedChangeListener(null)
                if (data.checkBox == null) {
                    binding.inputCheckbox.visibility = GONE
                } else {
                    visibility = VISIBLE
                    isChecked = data.checkBox.isSelected
                    text = data.checkBox.text
                }
                setOnCheckedChangeListener(this@FormTextViewHolder)
            }
        }
    }
}
