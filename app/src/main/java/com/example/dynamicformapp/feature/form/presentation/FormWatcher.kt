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

class RadioInputWatcher(private val onSelected: (Boolean) -> Unit) :
    CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(view: CompoundButton, isChecked: Boolean) {
        onSelected.invoke(isChecked)
    }
}

class CheckInputWatcher(private val onCheckChange: (Boolean) -> Unit) :
    CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(view: CompoundButton, isChecked: Boolean) {
        onCheckChange.invoke(isChecked)
    }
}