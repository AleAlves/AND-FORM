package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputRadioViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.domain.model.FormRadioVO
import com.example.dynamicformapp.feature.form.presentation.ChoiceSelectionWatcher

class FormRadioViewHolder<T>(private val binding: InputRadioViewBinding) :
    FormViewHolder<T>(binding.root) {

    private var id = ""

    private val watcher = ChoiceSelectionWatcher {
        onNewInput?.invoke(FormIO(currentPosition, value = id, isSelected = it))
    }

    override fun setupView(data: T?) {
        data as FormRadioVO
        id = data.id
        with(binding.inputRadio) {
            isChecked = data.isSelected
            isEnabled = data.isEnabled
            text = data.text
        }
    }

    override fun setupClickListeners() {
        binding.inputRadio.setOnCheckedChangeListener(watcher)
    }
}