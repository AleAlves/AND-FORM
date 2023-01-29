package com.example.dynamicformapp.feature.form.model


data class FormTextVO(
    var text: String = "",
    var requestFocus: Boolean,
    var isEnabled: Boolean,
    var hint: String = "",
    val subtitle: String = "",
    val inputType: Int,
    val maxSize: Int,
    val minSize: Int,
    val isSingleLine: Boolean,
    var error: String? = null,
    val checkBox: FormCheckVO? = null,
    override val onInput: ((FormData) -> Unit)
) : FormVO(onInput)

data class FormCheckVO(
    var text: String = "",
    var isEnabled: Boolean,
    var isSelected: Boolean,
    override val onInput: ((FormData) -> Unit)
) : FormVO(onInput)

data class FormRadioVO(
    val id: String,
    var text: String = "",
    var isEnabled: Boolean,
    var isSelected: Boolean,
    override val onInput: ((FormData) -> Unit)
) : FormVO(onInput)

abstract class FormVO(
    open val onInput: ((FormData) -> Unit)
)

data class FormData(
    val position: Int,
    val value: String = "",
    val isSelected: Boolean = false,
    var error: String? = null
)
