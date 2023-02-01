package com.example.dynamicformapp.feature.form.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.databinding.InputRulesViewItemBinding
import com.example.dynamicformapp.feature.form.domain.model.*
import com.example.dynamicformapp.feature.form.presentation.holder.*

class RulesAdapter : RecyclerView.Adapter<FormViewHolder<FormRuleSet>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FormViewHolder<FormRuleSet> {
        val inflater = LayoutInflater.from(parent.context)
        return RulesViewHolder(InputRulesViewItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FormViewHolder<FormRuleSet>, position: Int) {
        with(holder) {
            data = items.currentList[position]
        }
    }

    val items = AsyncListDiffer(this, object : DiffUtil.ItemCallback<FormRuleSet>() {
        override fun areItemsTheSame(oldItem: FormRuleSet, newItem: FormRuleSet): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FormRuleSet, newItem: FormRuleSet): Boolean {
            return oldItem == newItem
        }
    })

    override fun getItemCount(): Int = items.currentList.size
}