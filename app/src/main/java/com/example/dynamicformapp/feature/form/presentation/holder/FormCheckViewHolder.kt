package com.example.dynamicformapp.feature.form.presentation.holder

import android.content.Context
import android.view.LayoutInflater
import com.example.dynamicformapp.databinding.InputCheckViewBinding
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormInput

class FormCheckViewHolder(context: Context) : BaseFormViewHolder(context) {

    private val layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

    private var binding = InputCheckViewBinding.inflate(layoutInflater, this, true)

    init {
        binding.inputCheckbox.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked == view.isChecked) {
                onNewInput?.invoke(FormInput(currentPosition, isSelected = isChecked))
            }
        }
    }

    override fun setupView(data: Any?) {
        data as FormCheckVO
        with(binding) {
            inputCheckbox.isChecked = data.isSelected
            inputCheckbox.text = data.text
        }
    }
}