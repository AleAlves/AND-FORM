package com.example.dynamicformapp.feature.form.presentation


import android.content.res.Resources.NotFoundException
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.databinding.InputCheckViewBinding
import com.example.dynamicformapp.databinding.InputRadioViewBinding
import com.example.dynamicformapp.databinding.InputTextViewBinding
import com.example.dynamicformapp.feature.form.domain.model.*
import com.example.dynamicformapp.feature.form.presentation.holder.*


class FormAdapter() : RecyclerView.Adapter<FormViewHolder<FormVO>>() {

    companion object {
        private const val TEXT = 1
        private const val RADIO = 2
        private const val CHECKBOX = 3
        private const val UNKNOWN = 0
    }

    var onInput: ((FormIO) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder<FormVO> {
        val inflater = LayoutInflater.from(parent.context)
        val view: FormViewHolder<FormVO> = when (viewType) {
            TEXT -> FormTextViewHolder(
                InputTextViewBinding.inflate(inflater, parent, false)
            )
            RADIO -> FormRadioViewHolder(
                InputRadioViewBinding.inflate(inflater, parent, false)
            )
            CHECKBOX -> FormCheckViewHolder(
                InputCheckViewBinding.inflate(inflater, parent, false)
            )
            else -> throw NotFoundException("Invalid Form")
        }
        return view
    }

    override fun onBindViewHolder(holder: FormViewHolder<FormVO>, position: Int) {
        with(holder) {
            data = items.currentList[position]
            onInput = this@FormAdapter.onInput
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items.currentList[position]) {
            is FormTextVO -> TEXT
            is FormRadioVO -> RADIO
            is FormCheckVO -> CHECKBOX
            else -> UNKNOWN
        }
    }

    val items = AsyncListDiffer(this, object : DiffUtil.ItemCallback<FormVO>() {
        override fun areItemsTheSame(oldItem: FormVO, newItem: FormVO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FormVO, newItem: FormVO): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun getItemCount(): Int = items.currentList.size
}