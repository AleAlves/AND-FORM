package com.example.dynamicformapp.core.domain

import com.example.dynamicformapp.feature.form.model.FormInput

typealias UseCaseInput = ((FormInput) -> Unit)

interface UsaCaseValidations {
    fun onReadInput(input: FormInput)
}

abstract class BaseUseCase<T> : UsaCaseValidations {
    var isValid: Boolean = false
        set(value) {
            errorMessage = null
            field = value
        }
    var errorMessage: String? = null
    var inputListener: UseCaseInput = { _ -> }
    abstract operator fun invoke(input: UseCaseInput): T
}
