package com.example.dynamicformapp.feature.step.a.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.Input
import com.example.dynamicformapp.feature.form.model.FormCheckVO
import com.example.dynamicformapp.feature.form.model.FormTextVO
import javax.inject.Inject

class PasswordFormUseCase @Inject constructor() :
    BaseUseCase<FormTextVO>() {

    var password = ""
    var shouldSavePassword = false

    override fun invoke(validation: Input): FormTextVO {
        inputListener = validation
        return FormTextVO(
            inputHint = "Password",
            maxCharacters = 16,
            checkBox = FormCheckVO(
                caption = "Remember me",
                isChecked = false,
                ::onCheckBoxValidation
            ),
            onInputValidation = ::onInputValidation
        )
    }

    override fun onInputValidation(position: Int, value: String) {
        isValid = value.isNotEmpty()
        if (value.length >= 15) {
            isValid = false
            errorMessage = "Easy! do you want to protect account till the end of the next universe?"
        }
        password = value
        inputListener.invoke(position, value, errorMessage)
    }

    override fun onCheckBoxValidation(position: Int, value: Boolean) {
        shouldSavePassword = value
    }
}