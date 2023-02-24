package com.example.dynamicformapp.feature.form.domain

import android.text.InputType
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import javax.inject.Inject

open class GenericFormUseCase @Inject constructor() : FormUsaCase<FormTextVO>() {

    override val formVO: FormTextVO = FormTextVO(
        text = "",
        maxSize = 100,
        minSize = 0,
        requestFocus = false,
        isSingleLine = true,
        isEnabled = false,
        isReadOnly = true,
        hasCounter = false,
        gridSpan = 1,
        inputType = InputType.TYPE_CLASS_TEXT,
        onInput = ::onInput
    )
}