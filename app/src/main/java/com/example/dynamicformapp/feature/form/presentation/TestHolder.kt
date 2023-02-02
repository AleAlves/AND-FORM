package com.example.dynamicformapp.feature.form.presentation

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicformapp.R
import com.example.dynamicformapp.core.util.toEditable
import com.example.dynamicformapp.feature.form.domain.model.FormVO

interface OnClick {
    fun clickPrice(item: String, price: String, index: Int)
}

class InvoiceProductsListAdapter() :
    RecyclerView.Adapter<InvoiceProductsListAdapter.MyViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.input_text_view, parent, false)
        val myview = MyViewHolder(itemView)
        myview.setListners()
        return myview
    }

    private fun bindViews(holder: MyViewHolder, position: Int) {
        val data = items.currentList[position]
        holder.edtPrice.text = data.text.toEditable()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        bindViews(holder, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val edtPrice = itemView.findViewById<EditText>(R.id.input_view_editext)

        fun setListners() {
            edtPrice.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    onClick?.invoke(str.toString())
                }
            })
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