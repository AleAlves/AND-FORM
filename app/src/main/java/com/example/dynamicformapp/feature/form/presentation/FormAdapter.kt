package com.example.dynamicformapp.feature.form.presentation


import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormTextVO
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.holder.FormCheckViewHolder
import com.example.dynamicformapp.feature.form.presentation.holder.BaseFormViewHolder
import com.example.dynamicformapp.feature.form.presentation.holder.FormTextViewHolder


class FormViewHolder(val view: BaseFormViewHolder) : RecyclerView.ViewHolder(view)

class FormAdapter : RecyclerView.Adapter<FormViewHolder>() {

    companion object {
        private const val TEXT = 1
        private const val CHECKBOX = 2
        private const val UNKNOWN = 0
    }

    var onTextInput: ((Int, String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val inputType = when (viewType) {
            TEXT -> FormTextViewHolder(parent.context)
            CHECKBOX -> FormCheckViewHolder(parent.context)
            else -> FormCheckViewHolder(parent.context)
        }
        return FormViewHolder(inputType)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        with(holder.view) {
            data = differ.currentList[position]
            currentPosition = position
            onTextInput = this@FormAdapter.onTextInput
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]) {
            is FormTextVO -> TEXT
            is FormCheckVO -> CHECKBOX
            else -> UNKNOWN
        }
    }

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<FormVO>() {
        override fun areItemsTheSame(oldItem: FormVO, newItem: FormVO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FormVO, newItem: FormVO): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun getItemCount(): Int = differ.currentList.size
}