package com.example.dynamicformapp.feature.form.model


data class FormTextVO(
    var inputHint: String = "",
    val subtitle: String = "",
    var inputText: String = "",
    var inputError: String? = null,
    val maxCharacters: Int = 0,
    val checkBox: FormCheckVO? = null,
    override val onReadInput: ((FormInput) -> Unit)
) : FormVO(onReadInput)

data class FormCheckVO(
    var text: String,
    var isSelected: Boolean,
    override val onReadInput: ((FormInput) -> Unit)
) : FormVO(onReadInput)

data class FormRadioVO(
    val id: String,
    var text: String,
    var isSelected: Boolean,
    override val onReadInput: ((FormInput) -> Unit)
) : FormVO(onReadInput)

abstract class FormVO(
    open val onReadInput: ((FormInput) -> Unit)
)

data class FormInput(
    val position: Int,
    val value: String = "",
    val isSelected: Boolean = false,
    var error: String? = null
)