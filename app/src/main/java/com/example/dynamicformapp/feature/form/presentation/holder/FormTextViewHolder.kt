package com.example.dynamicformapp.feature.form.presentation.holder

import android.annotation.SuppressLint
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import com.example.dynamicformapp.R
import com.example.dynamicformapp.core.util.toEditable
import com.example.dynamicformapp.databinding.InputTextViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import com.example.dynamicformapp.feature.form.presentation.CheckSelectionWatcher
import com.example.dynamicformapp.feature.form.presentation.TextInputWatcher


class FormTextViewHolder<T>(
    private val binding: InputTextViewBinding
) : FormViewHolder<T>(binding.root) {

    private val textWatcher = TextInputWatcher {
        if (binding.inputViewEditext.hasFocus()) {
            onNewInput?.invoke(
                FormIO(
                    position = currentPosition,
                    value = it,
                    isSelected = binding.inputCheckbox.isChecked
                )
            )
        }
    }

    private val checkWatcher = CheckSelectionWatcher {
        onNewInput?.invoke(
            FormIO(
                currentPosition,
                value = binding.inputViewEditext.text.toString(),
                isSelected = it
            )
        )
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun setupView(data: T?) {
        data as FormTextVO
        with(binding) {
            inputTextViewSubtitle.text = data.subtitle
            inputViewEditext.let { edit ->
                edit.removeTextChangedListener(textWatcher)
                if (data.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    edit.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    edit.transformationMethod = null
                }
                edit.setRawInputType(data.inputType)
                edit.filters = arrayOf(InputFilter.LengthFilter(data.maxSize))
                edit.text = data.text.toEditable()
                edit.setSelection(edit.text?.length ?: 0)
                edit.error = data.error
                edit.hint = data.hint
                edit.addTextChangedListener(textWatcher)
                inputTextViewCounter.text = "${edit.text.toString().length}/${data.maxSize}"
                if (edit.text.toString()
                        .isNotEmpty() && edit.text.toString().length < data.minSize
                ) {
                    inputTextViewCounter.setTextColor(view.context.getColor(R.color.red))
                } else {
                    inputTextViewCounter.setTextColor(view.context.getColor(R.color.white))
                }
                if (data.requestFocus && !edit.hasFocus()) {
                    edit.post { edit.requestFocus() }
                }
                edit.isEnabled = data.isEnabled
            }

            if (data.checkBox == null) {
                binding.inputCheckbox.visibility = GONE
            } else {
                binding.inputCheckbox.setOnCheckedChangeListener(null)
                binding.inputCheckbox.visibility = VISIBLE
                binding.inputCheckbox.isChecked = data.checkBox.isSelected
                binding.inputCheckbox.text = data.checkBox.text
                binding.inputCheckbox.setOnCheckedChangeListener(checkWatcher)
            }
        }
    }
}