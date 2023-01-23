package com.example.dynamicformapp.feature.form.presentation

import android.text.Editable
import android.text.TextWatcher
import android.widget.CompoundButton

class TextInputWatcher(private val onTextChanged: (String) -> Unit) : TextWatcher {

    override fun afterTextChanged(s: Editable) {}

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        onTextChanged.invoke(s.toString())
    }
}

class CheckWatcher(private val onSelectionChanged: (Boolean) -> Unit) :
    CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        onSelectionChanged.invoke(p1)
    }

}