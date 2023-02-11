package com.example.dynamicformapp.feature.step.password.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.databinding.InputRulesViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormRule
import com.example.dynamicformapp.feature.step.password.presentation.adapter.PasswordRuleAdapter

class PasswordRulesView(
    context: Context, attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {

    private val adapter = PasswordRuleAdapter()
    private val layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

    private var binding = InputRulesViewBinding.inflate(
        layoutInflater, this, true
    )

    fun setData(forms: List<FormRule>?) {
        adapter.items.submitList(forms)
        adapter.notifyDataSetChanged()
    }

    init {
        binding.formRulesRecycler.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }
        binding.formRulesRecycler.adapter = adapter
    }
}