package com.example.dynamicformapp.feature.form.domain.model

abstract class FormVO {
    abstract val onInput: ((FormData) -> Unit)
}

data class FormData(
    val position: Int,
    val value: String = "",
    val isSelected: Boolean = false,
    var error: String? = null
)
