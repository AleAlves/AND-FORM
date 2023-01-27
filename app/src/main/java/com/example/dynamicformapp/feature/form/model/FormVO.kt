package com.example.dynamicformapp.feature.form.model


data class FormTextVO(
    var text: String = "",
    var requestFocus: Boolean = false,
    var isEnabled: Boolean = true,
    var hint: String = "",
    val subtitle: String = "",
    val inputType: Int,
    val maxSize: Int,
    val minSize: Int,
    val isSingleLine: Boolean = true,
    var error: String? = null,
    val checkBox: FormCheckVO? = null,
    override val onInput: ((FormInput) -> Unit)
) : FormVO(onInput)

data class FormCheckVO(
    var text: String = "",
    var isEnabled: Boolean = true,
    var isSelected: Boolean,
    override val onInput: ((FormInput) -> Unit)
) : FormVO(onInput)

data class FormRadioVO(
    val id: String,
    var text: String = "",
    var isEnabled: Boolean = true,
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