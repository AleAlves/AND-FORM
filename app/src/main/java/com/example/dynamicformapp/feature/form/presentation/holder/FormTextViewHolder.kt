package com.example.dynamicformapp.feature.form.presentation.holder

import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.dynamicformapp.R
import com.example.dynamicformapp.core.util.onListener
import com.example.dynamicformapp.core.util.toEditable
import com.example.dynamicformapp.databinding.InputTextViewBinding
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormTextVO
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.TextInputWatcher


class FormTextViewHolder(
    private val binding: InputTextViewBinding
) : FormViewHolder(binding.root) {

    private val watcher = TextInputWatcher {
        if (binding.inputViewEditext.hasFocus()) {
            onNewInput?.invoke(FormInput(currentPosition, it, binding.inputCheckbox.isChecked))
        }
    }

    init {
        binding.inputCheckbox.onListener {
            onNewInput?.invoke(
                FormInput(currentPosition, binding.inputViewEditext.text.toString(), it)
            )
        }
    }

    override fun setupView(data: FormVO?) {
        data as FormTextVO
        Log.e("WOW", currentPosition.toString())
        with(binding) {
            inputTextViewSubtitle.text = data.subtitle
            inputViewEditext.let { edit ->
                edit.removeTextChangedListener(watcher)
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
                edit.addTextChangedListener(watcher)
                inputTextViewCounter.text = "${edit.text.toString().length}/${data?.maxSize}"
                if (edit.text.toString()
                        .isNotEmpty() && edit.text.toString().length < data.minSize
                ) {
                    inputTextViewCounter.setTextColor(view.context.getColor(R.color.red))
                } else {
                    inputTextViewCounter.setTextColor(view.context.getColor(R.color.white))
                }
                Log.e("WOW", "Max ${data.maxSize}")
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