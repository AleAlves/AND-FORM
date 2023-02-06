package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputRadioViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormRadioVO

class FormRadioViewHolder<T>(private val binding: InputRadioViewBinding) :
    FormViewHolder<T>(binding.root) {

    private var id = ""

    override fun setupView(data: T?) {
        data as FormRadioVO
        id = data.id
        with(binding.inputRadio) {
            setOnCheckedChangeListener(null)
            isChecked = data.isSelected
            isEnabled = data.isEnabled
            text = data.text
        }
        binding.inputRadio.setOnCheckedChangeListener(this)
    }
}