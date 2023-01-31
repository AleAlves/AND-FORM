package com.example.dynamicformapp.feature.form.model

data class FormCheckVO(
    var text: String = "",
    var isEnabled: Boolean,
    var isSelected: Boolean,
    override val onInput: ((FormData) -> Unit)
) : FormVO()