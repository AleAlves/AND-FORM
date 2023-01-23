package com.example.dynamicformapp.feature.step.login.domain.usecase

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

    override fun invoke(input: UseCaseInput): FormTextVO {
        inputListener = input
        return FormTextVO(
            inputHint = "Password",
            maxCharacters = 16,
            checkBox = FormCheckVO(
                text = "Remember me",
                isSelected = false,
                ::onReadSelectionInput
            ),
            onInput = ::onReadInput
        )
    }

    override fun onReadInput(input: FormInput) {
        isValid = input.value.isNotEmpty()
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