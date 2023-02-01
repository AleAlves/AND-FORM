package com.example.dynamicformapp.feature.form.presentation.holder


import android.graphics.Paint
import com.example.dynamicformapp.databinding.InputRulesViewItemBinding
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet

class RulesViewHolder<T>(
    private val binding: InputRulesViewItemBinding
) : FormViewHolder<T>(binding.root) {

    override fun setupView(data: T?) {
        data as FormRuleSet
        with(binding.inputRulesTextview) {
            text = data.text
            paintFlags = if (data.isValid) {
                paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
}