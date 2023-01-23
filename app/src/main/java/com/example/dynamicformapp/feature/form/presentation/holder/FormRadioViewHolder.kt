package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputRadioViewBinding
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormRadioVO
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.CheckWatcher
import com.example.dynamicformapp.feature.form.presentation.FormViewHolder

class FormRadioViewHolder(private val binding: InputRadioViewBinding) :
    FormViewHolder(binding.root) {

    private val watcher = CheckWatcher {
        onNewInput?.invoke(FormInput(currentPosition, isSelected = it))
    }

    init {
        binding.inputRadio.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked != view.isChecked) {
                onNewInput?.invoke(FormInput(currentPosition, isSelected = isChecked))
            }
        }
    }

    override fun setupView(data: FormVO?) {
        data as FormRadioVO
        with(binding) {
            inputRadio.setOnCheckedChangeListener(null)
            inputRadio.isChecked = data.isSelected
            inputRadio.text = data.text
            inputRadio.setOnCheckedChangeListener(watcher)
        }
    }
}