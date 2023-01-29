package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputRadioViewBinding
import com.example.dynamicformapp.feature.form.model.FormData
import com.example.dynamicformapp.feature.form.model.FormRadioVO
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.ChoiceSelectionWatcher

class FormRadioViewHolder(private val binding: InputRadioViewBinding) :
    FormViewHolder(binding.root) {

    private var id = ""

    private val watcher = ChoiceSelectionWatcher {
        onNewInput?.invoke(FormData(currentPosition, value = id, isSelected = it))
    }

    override fun setupView(data: FormVO?) {
        data as FormRadioVO
        id = data.id
        with(binding.inputRadio) {
            setOnCheckedChangeListener(null)
            isChecked = data.isSelected
            isEnabled = data.isEnabled
            text = data.text
            setOnCheckedChangeListener(watcher)
        }
    }
}