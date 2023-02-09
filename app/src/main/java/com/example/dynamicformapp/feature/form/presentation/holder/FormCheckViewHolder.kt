package com.example.dynamicformapp.feature.form.presentation.holder

import android.widget.LinearLayout
import com.example.dynamicformapp.databinding.InputCheckViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormCheckVO

class FormCheckViewHolder<T>(private val binding: InputCheckViewBinding) :
    FormViewHolder<T>(binding.root) {

    override fun setupView(data: T?) {
        data as FormCheckVO
        inputSelected = data.isSelected

        binding.root.layoutParams = LinearLayout.LayoutParams(
            if (data.fill) LinearLayout.LayoutParams.MATCH_PARENT else LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        with(binding.inputCheckbox) {
            setOnCheckedChangeListener(null)
            isChecked = data.isSelected
            isEnabled = data.isEnabled
            text = data.text
        }
        binding.inputCheckbox.setOnCheckedChangeListener(this)
    }
}