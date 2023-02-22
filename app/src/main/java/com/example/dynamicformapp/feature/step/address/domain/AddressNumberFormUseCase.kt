package com.example.dynamicformapp.feature.step.address.domain

import android.content.Context
import android.text.InputType
import com.example.dynamicformapp.R
import com.example.dynamicformapp.feature.form.domain.OutputListener
import com.example.dynamicformapp.feature.form.domain.FormUsaCase
import com.example.dynamicformapp.feature.form.domain.model.FormCheckVO
import com.example.dynamicformapp.feature.form.domain.model.FormTextVO
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AddressNumberFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : FormUsaCase<FormTextVO>() {


    override val formVO: FormTextVO = FormTextVO(
        hint = context.getString(R.string.address_number_input_hint),
        helper = context.getString(R.string.address_number_input_helper),
        maxSize = 5,
        minSize = 1,
        requestFocus = false,
        isSingleLine = true,
        isEnabled = true,
        checkBox = FormCheckVO(
            text = "No number", onInput = ::onInput
        ),
        inputType = InputType.TYPE_CLASS_NUMBER,
        onInput = ::onInput,
    )

    override fun onValidation(output: OutputListener) {
        ruleSetListener = output
        onRuleSetValidations(formVO)
    }
}