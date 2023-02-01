package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputCheckViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormCheckVO
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.presentation.CheckSelectionWatcher

class FormCheckViewHolder<T>(private val binding: InputCheckViewBinding) :
    FormViewHolder<T>(binding.root) {

    private val watcher = CheckSelectionWatcher {
        onNewInput?.invoke(FormIO(currentPosition, isSelected = it))
    }

    override fun setupView(data: T?) {
        data as FormCheckVO
        with(binding.inputCheckbox) {
            setOnCheckedChangeListener(null)
            isChecked = data.isSelected
            isEnabled = data.isEnabled
            text = data.text
            setOnCheckedChangeListener(watcher)
        }
    }
}