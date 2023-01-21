package com.example.dynamicformapp.feature.form.presentation.holder

import android.content.Context
import android.view.LayoutInflater
import com.example.dynamicformapp.databinding.InputCheckViewBinding

class FormCheckViewHolder(context: Context) : BaseFormViewHolder(context) {

    private val layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

    private var binding = InputCheckViewBinding.inflate(layoutInflater, this, true)

    init {
        binding.inputCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onCheckInput?.invoke(currentPosition, isChecked)
        }
    }

    override fun setupView(data: Any?) {
        with(binding) {
            binding.inputCheckbox.isChecked = true
            binding.inputCheckbox.text = "I agree with terms and stuff"
        }
    }
}