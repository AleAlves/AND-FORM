package com.example.dynamicformapp.feature.form.presentation.ui

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
import com.example.dynamicformapp.feature.form.presentation.adapter.FormAdapter


class FormInputView(
    context: Context,
    attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {

    private val layoutInflater: LayoutInflater get() = LayoutInflater.from(context)
    private val formAdapter = FormAdapter()

    var onInput: ((FormIO) -> Unit)? = null
        set(value) {
            formAdapter.onInput = value
            field = value
        }

    private var binding = FormViewBinding.inflate(
        layoutInflater,
        this,
        true
    )

    fun setData(forms: List<FormVO>) {
        formAdapter.items.submitList(forms)
    }

    fun notifyChangeAt(position: Int) {
        formAdapter.notifyItemChanged(position)
    }

    fun updateForm() {
        formAdapter.notifyItemChanged(0, formAdapter.items.currentList.size)
    }

    init {
        with(binding.formInputRecycler) {
            layoutManager = GridLayoutManager(context, 3).apply {
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return formAdapter.items.currentList[position].gridSpan
                    }
                }
            }
            animation = null
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            setHasFixedSize(true)
            adapter = formAdapter
        }
    }
}