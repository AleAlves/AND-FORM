package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.text.InputType
import com.example.dynamicformapp.feature.form.domain.BaseFormUsaCase
import com.example.dynamicformapp.feature.form.domain.FormInput
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormData
import com.example.dynamicformapp.feature.form.model.FormTextVO
import javax.inject.Inject

class PasswordFormUseCase @Inject constructor() :
    BaseFormUsaCase<FormTextVO>() {

    var password = ""
    var shouldSavePassword = false

    override val formVO: FormTextVO = FormTextVO(
        hint = "Password",
        maxSize = 15,
        minSize = 6,
        checkBox = FormCheckVO(
            text = "Remember me",
            isSelected = false,
            isEnabled = true,
            onInput = ::onReadSelectionInput
        ),
        isEnabled = true,
        isSingleLine = true,
        requestFocus = false,
        onInput = ::onReadInput,
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
    )

    override fun invoke(input: FormInput): FormTextVO {
        inputListener = input
        return formVO
    }

    override fun onReadInput(input: FormData) {
        isValid = input.value.isNotEmpty()
        isValid = input.value.length < formVO.maxSize && input.value.length > formVO.minSize
        if (input.value.length >= 15) {
            isValid = false
            errorMessage = "Easy! do you want to protect account till the end of the next universe?"
        }
        password = input.value
        input.error = errorMessage
        inputListener.invoke(input)
    }

    override fun onReadSelectionInput(input: FormData) {
        shouldSavePassword = input.isSelected
    }

}