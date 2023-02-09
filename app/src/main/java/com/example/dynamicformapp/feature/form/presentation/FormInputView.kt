package com.example.dynamicformapp.feature.form.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.dynamicformapp.databinding.FormViewBinding
import com.example.dynamicformapp.feature.form.domain.model.FormIO
import com.example.dynamicformapp.feature.form.domain.model.FormVO


class FormInputView(
    context: Context,
    attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {

    private val layoutInflater: LayoutInflater get() = LayoutInflater.from(context)
    private val adapter = FormAdapter()


    var onInput: ((FormIO) -> Unit)? = null
        set(value) {
            adapter.onInput = value
            field = value
        }

    private var binding = FormViewBinding.inflate(
        layoutInflater,
        this,
        true
    )

    fun setData(forms: List<FormVO>) {
        adapter.items.submitList(forms)
    }

    fun notifyChangeAt(position: Int) {
        adapter.notifyItemChanged(position)
    }

    fun refresh() {
        adapter.notifyItemChanged(0, adapter.items.currentList.size)
    }

    init {
        binding.formInputRecycler.layoutManager = GridLayoutManager(context, 3).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return adapter.items.currentList[position].gridSpan
                }
            }
        }
        (binding.formInputRecycler.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
        binding.formInputRecycler.animation = null
        binding.formInputRecycler.setHasFixedSize(true)
        binding.formInputRecycler.adapter = adapter
    }
}