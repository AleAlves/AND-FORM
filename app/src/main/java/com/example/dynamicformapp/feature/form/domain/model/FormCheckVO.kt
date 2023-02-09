package com.example.dynamicformapp.feature.form.domain.model

data class FormCheckVO(
    var isSelected: Boolean = false,
    override var text: String = "",
    override var isEnabled: Boolean = true,
    override var isReadOnly: Boolean = false,
    override var fill: Boolean = true,
    override var gridSpan: Int = 3,
    override val onInput: ((FormIO) -> Unit)
) : FormVO()