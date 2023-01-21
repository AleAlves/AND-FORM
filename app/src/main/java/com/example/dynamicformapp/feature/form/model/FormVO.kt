package com.example.dynamicformapp.feature.form.model


data class FormTextVO(
    var inputHint: String = "",
    val subtitle: String = "",
    var inputText: String = "",
    var inputError: String? = null,
    val maxCharacters: Int = 0,
    val checkBox: FormCheckVO? = null,
    var onInputValidation: ((Int, String) -> Unit)
) : FormVO()

data class FormCheckVO(
    var caption: String,
    var isChecked: Boolean,
    var onInputValidation: ((Int, Boolean) -> Unit)
) : FormVO()

abstract class FormVO
