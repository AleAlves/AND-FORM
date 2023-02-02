package com.example.dynamicformapp.feature.form.presentation.holder


import android.graphics.Paint
import com.example.dynamicformapp.databinding.InputRulesViewItemBinding
import com.example.dynamicformapp.feature.form.domain.model.FormRule

class RulesViewHolder<T>(
    private val binding: InputRulesViewItemBinding
) : FormViewHolder<T>(binding.root) {

    override fun setupView(data: T?) {
        data as FormRule
        with(binding.inputRulesTextview) {
            text = data.name
            paintFlags = if (data.isValid) {
                paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun setupClickListeners() {

    }
}