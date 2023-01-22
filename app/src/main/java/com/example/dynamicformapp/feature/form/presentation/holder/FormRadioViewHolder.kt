package com.example.dynamicformapp.feature.form.presentation.holder

import android.content.Context
import android.view.LayoutInflater
import com.example.dynamicformapp.databinding.InputRadioViewBinding
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormRadioVO

class FormRadioViewHolder(context: Context) : BaseFormViewHolderImpl(context) {

    private val layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

    private var binding = InputRadioViewBinding.inflate(layoutInflater, this, true)

    init {
        binding.inputRadio.setOnClickListener {
            onNewInput?.invoke(FormInput(currentPosition, isSelected = it.isSelected))
        }
    }

    override fun setupView(data: Any?) {
        data as FormRadioVO
        with(binding) {
            inputRadio.text = data.text
            inputRadio.isSelected = data.isSelected
        }
    }
}