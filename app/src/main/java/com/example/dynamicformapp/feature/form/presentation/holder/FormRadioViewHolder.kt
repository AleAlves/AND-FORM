package com.example.dynamicformapp.feature.form.presentation.holder

import com.example.dynamicformapp.databinding.InputRadioViewBinding
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormRadioVO
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.CheckWatcher
import com.example.dynamicformapp.feature.form.presentation.FormViewHolder

class FormRadioViewHolder(private val binding: InputRadioViewBinding) :
    FormViewHolder(binding.root) {

    private var id = ""

    private val watcher = CheckWatcher {
        onNewInput?.invoke(FormInput(currentPosition, value = id, isSelected = it))
    }

    override fun setupView(data: FormVO?) {
        data as FormRadioVO
        id = data.id
        with(binding) {
            inputRadio.setOnCheckedChangeListener(null)
            inputRadio.isChecked = data.isSelected
            inputRadio.text = data.text
            inputRadio.setOnCheckedChangeListener(watcher)
        }
    }
}