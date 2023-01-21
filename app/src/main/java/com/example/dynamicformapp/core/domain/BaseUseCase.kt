package com.example.dynamicformapp.core.domain

typealias Input = ((Int, String, String?) -> Unit)


interface UsaCaseValidations {
    fun onInputValidation(position: Int, value: String)
    fun onCheckBoxValidation(position: Int, value: Boolean) {}
}

abstract class BaseUseCase<T> : UsaCaseValidations {
    var isValid: Boolean = false
        set(value) {
            errorMessage = null
            field = value
        }
    var errorMessage: String? = null
    var inputListener: Input = { _, _, _ -> }
    abstract operator fun invoke(validation: Input): T
}
