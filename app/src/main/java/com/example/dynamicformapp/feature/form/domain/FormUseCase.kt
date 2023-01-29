package com.example.dynamicformapp.feature.form.domain

import com.example.dynamicformapp.core.domain.BaseUseCase
import com.example.dynamicformapp.feature.form.model.FormData

typealias FormInput = ((FormData) -> Unit)

interface FormUsaCaseInput {
    fun onReadInput(input: FormData)
    fun onReadSelectionInput(input: FormData) {}
}

abstract class BaseFormUsaCase<T> : FormUsaCaseInput, BaseUseCase<T, FormInput>() {

    protected abstract val formVO: T

    var isValid: Boolean = false
        set(value) {
            errorMessage = null
            field = value
        }
    var errorMessage: String? = null
    var inputListener: FormInput = { _ -> }
}
