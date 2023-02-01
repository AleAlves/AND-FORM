package com.example.dynamicformapp.feature.form.domain.model

data class FormCheckVO(
    var isSelected: Boolean = false,
    override var text: String = "",
    override var isEnabled: Boolean = true,
    override val onInput: ((FormIO) -> Unit)
) : FormVO()