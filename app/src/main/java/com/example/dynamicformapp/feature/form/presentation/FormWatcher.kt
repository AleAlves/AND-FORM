package com.example.dynamicformapp.feature.form.presentation

import android.text.Editable
import android.text.TextWatcher
import android.widget.CompoundButton

class TextInputWatcher(private val onTextChanged: (String) -> Unit) : TextWatcher {

    private var value: String = ""

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
        if (value != s.toString()) {
            value = s.toString()
            onTextChanged.invoke(s.toString())
        }
    }
}

class ChoiceSelectionWatcher(private val onSelectionChanged: (Boolean) -> Unit) :
    CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(view: CompoundButton, isChecked: Boolean) {
        onSelectionChanged.invoke(isChecked)
    }
}

class CheckSelectionWatcher(private val onCheckChange: (Boolean) -> Unit) :
    CompoundButton.OnCheckedChangeListener {
    override fun onCheckedChanged(view: CompoundButton?, isChecked: Boolean) {
        if (isChecked == view?.isChecked) {
            onCheckChange.invoke(isChecked)
        }
    }
}