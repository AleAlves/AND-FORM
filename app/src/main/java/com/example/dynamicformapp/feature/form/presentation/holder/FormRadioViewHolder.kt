package com.example.dynamicformapp.feature.form.presentation.holder

import android.widget.LinearLayout
import com.example.dynamicformapp.databinding.InputRadioViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormRadioVO

class FormRadioViewHolder<T>(private val binding: InputRadioViewBinding) :
    FormViewHolder<T>(binding.root) {

    override fun setupView(data: T?) {
        data as FormRadioVO
        inputValue = data.id
        inputSelected = data.isSelected

        binding.root.layoutParams = LinearLayout.LayoutParams(
            if (data.fill) LinearLayout.LayoutParams.MATCH_PARENT else LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        with(binding.inputRadio) {
            setOnCheckedChangeListener(null)
            isChecked = data.isSelected
            isEnabled = data.isEnabled
            text = data.text
        }
        binding.inputRadio.setOnCheckedChangeListener(this)
    }
}