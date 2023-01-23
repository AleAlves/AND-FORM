package com.example.dynamicformapp.feature.form.model


data class FormTextVO(
    var inputHint: String = "",
    val subtitle: String = "",
    var inputText: String = "",
    val inputType: Int,
    val maxSize: Int,
    val minSize: Int,
    var inputError: String? = null,
    val checkBox: FormCheckVO? = null,
    override val onInput: ((FormInput) -> Unit)
) : FormVO(onInput)

data class FormCheckVO(
    var text: String,
    var isSelected: Boolean,
    override val onInput: ((FormInput) -> Unit)
) : FormVO(onInput)

abstract class FormVO(
    open val onInput: ((FormInput) -> Unit)
)

data class FormInput(
    val position: Int,
    val value: String = "",
    val isSelected: Boolean = false,
    var error: String? = null
)