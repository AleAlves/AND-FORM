package com.example.dynamicformapp.feature.form.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dynamicformapp.databinding.FormViewBinding
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormVO

class FormInputView(
    context: Context,
    attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {

    private val adapter = FormAdapter()
    private val layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

    var onReadInput: ((FormInput) -> Unit)? = null
        set(value) {
            adapter.onReadInput = value
            field = value
        }

    private var binding = FormViewBinding.inflate(
        layoutInflater,
        this,
        true
    )

    fun setData(forms: List<FormVO>) {
        adapter.differ.submitList(forms)
    }

    fun notifyChangeAt(position: Int) {
        adapter.notifyItemChanged(position)
    }

    init {
        binding.formInputRecycler.layoutManager = LinearLayoutManager(context)
        binding.formInputRecycler.adapter = adapter
    }
}