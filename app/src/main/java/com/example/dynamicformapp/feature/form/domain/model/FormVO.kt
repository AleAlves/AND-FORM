package com.example.dynamicformapp.feature.form.domain.model

abstract class FormVO {
    abstract var text: String
    abstract var isEnabled: Boolean
    abstract var isReadOnly: Boolean
    abstract var fill: Boolean
    abstract val onInput: ((FormIO) -> Unit)
}

data class FormIO(
    val position: Int,
    val value: String = "",
    var error: String? = null,
    val isSelected: Boolean = false
)
