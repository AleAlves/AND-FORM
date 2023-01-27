package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputCheckViewBinding
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormVO

class FormCheckViewHolder(private val binding: InputCheckViewBinding) :
    FormViewHolder(binding.root) {

    init {
        binding.inputCheckbox.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked == view.isChecked) {
                onNewInput?.invoke(FormInput(currentPosition, isSelected = isChecked))
            }
        }
    }

    override fun setupView(data: FormVO?) {
        data as FormCheckVO
        with(binding) {
            inputCheckbox.isChecked = data.isSelected
            inputCheckbox.text = data.text
        }
    }
}