package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputCheckViewBinding
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormData
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.CheckSelectionWatcher

class FormCheckViewHolder(private val binding: InputCheckViewBinding) :
    FormViewHolder(binding.root) {

    private val watcher = CheckSelectionWatcher {
        onNewInput?.invoke(FormData(currentPosition, isSelected = it))
    }

    override fun setupView(data: FormVO?) {
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