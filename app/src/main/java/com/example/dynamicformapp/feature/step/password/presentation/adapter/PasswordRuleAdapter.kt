package com.example.dynamicformapp.feature.step.password.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.databinding.InputRulesViewItemBinding
import com.example.dynamicformapp.feature.form.domain.model.*
import com.example.dynamicformapp.feature.form.presentation.holder.*
import com.example.dynamicformapp.feature.step.password.presentation.ui.RulesViewHolder

class PasswordRuleAdapter : RecyclerView.Adapter<FormViewHolder<FormRule>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FormViewHolder<FormRule> {
        val inflater = LayoutInflater.from(parent.context)
        return RulesViewHolder(InputRulesViewItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FormViewHolder<FormRule>, position: Int) {
        with(holder) {
            data = items.currentList[position]
        }
    }

    val items = AsyncListDiffer(this, object : DiffUtil.ItemCallback<FormRule>() {
        override fun areItemsTheSame(oldItem: FormRule, newItem: FormRule): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FormRule, newItem: FormRule): Boolean {
            return oldItem == newItem
        }
    })

    override fun getItemCount(): Int = items.currentList.size
}