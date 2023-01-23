package com.example.dynamicformapp.feature.step.login.domain.usecase

import android.content.Context
import android.text.InputType
import com.example.dynamicformapp.R
import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.UseCaseInput
import com.example.dynamicformapp.feature.form.model.FormInput
import com.example.dynamicformapp.feature.form.model.FormTextVO
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EmailFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseUseCase<FormTextVO>() {

    var email = ""

    override val formVO: FormTextVO = FormTextVO(
        inputHint = context.getString(R.string.login_email_input_hint),
        subtitle = context.getString(R.string.login_email_input_helper),
        maxSize = 50,
        minSize = 5,
        onInput = ::onReadInput,
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    )

    override fun invoke(input: UseCaseInput): FormTextVO {
        inputListener = input
        return formVO
    }

    override fun onReadInput(input: FormInput) {
        isValid = input.value.contains("@") && input.value.contains(".").and(input.value.isNotEmpty())
        isValid = input.value.length < formVO.maxSize && input.value.length > formVO.minSize
        if (input.value.contains(" ")) {
            isValid = false
            errorMessage = "Invalid email"
        }
        email = input.value
        input.error = errorMessage
        inputListener.invoke(input)
    }

}