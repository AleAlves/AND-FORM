package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.text.InputType
import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.UseCaseInput
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormTextVO
import javax.inject.Inject

class PasswordFormUseCase @Inject constructor() :
    BaseUseCase<FormTextVO>() {

    var password = ""
    var shouldSavePassword = false

    override val formVO: FormTextVO = FormTextVO(
        inputHint = "Password",
        maxSize = 15,
        minSize = 6,
        checkBox = FormCheckVO(
            text = "Remember me",
            isSelected = false,
            ::onReadSelectionInput
        ),
        onInput = ::onReadInput,
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
    )

    override fun invoke(input: UseCaseInput): FormTextVO {
        inputListener = input
        return formVO
    }

    override fun onReadInput(input: FormInput) {
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

    override fun onReadSelectionInput(input: FormInput) {
        shouldSavePassword = input.isSelected
    }

}