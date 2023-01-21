package com.example.dynamicformapp.feature.step.a.domain

import android.content.Context
import com.example.dynamicformapp.R
import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.core.domain.Input
import com.example.dynamicformapp.feature.form.model.FormTextVO
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class EmailFormUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseUseCase<FormTextVO>() {

    var email = ""

    override fun invoke(validation: Input): FormTextVO {
        inputListener = validation
        return FormTextVO(
            inputHint = context.getString(R.string.login_email_input_hint),
            subtitle = context.getString(R.string.login_email_input_helper),
            maxCharacters = 50,
            onInputValidation = ::onInputValidation
        )
    }

    override fun onInputValidation(position: Int, value: String) {
        var error: String? = null
        isValid = value.contains("@") && value.contains(".").and(value.isNotEmpty())
        if (value.contains(" ")) {
            error = "Invalid email"
            isValid = false
        }
        email = value
        inputListener.invoke(position, value, error)
    }
}