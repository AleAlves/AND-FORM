package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputCheckViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormCheckVO
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.presentation.CheckInputWatcher

class FormCheckViewHolder<T>(private val binding: InputCheckViewBinding) :
    FormViewHolder<T>(binding.root) {

    private val watcher = CheckInputWatcher {
        onInput?.invoke(FormIO(adapterPosition, isSelected = it))
    }

    override fun setupView(data: T?) {
        data as FormCheckVO
        with(binding.inputCheckbox) {
            setOnCheckedChangeListener(null)
            isChecked = data.isSelected
            isEnabled = data.isEnabled
            text = data.text
        }
        binding.inputCheckbox.setOnCheckedChangeListener(watcher)

    }

    override fun onTextChange(value: String) {}
}