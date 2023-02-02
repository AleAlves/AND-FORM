package com.example.dynamicformapp.feature.form.domain.model

data class FormTextVO(
    var requestFocus: Boolean,
    var hint: String = "",
    val subtitle: String = "",
    val inputType: Int,
    val maxSize: Int,
    val minSize: Int,
    val isSingleLine: Boolean,
    var error: String? = null,
    val checkBox: FormCheckVO? = null,
    val validation: FormValidation,
    override var text: String = "",
    override var isEnabled: Boolean = true,
    override var isReadOnly: Boolean = false,
    override var fill: Boolean = true,
    override val onInput: ((FormIO) -> Unit),
) : FormVO()

data class FormValidation(
    var rules: List<FormRule>,
    var hasErrors: Boolean = false,
    val onRuleCallback: (FormValidation) -> Unit
)

data class FormRule(
    val regex: Regex,
    val name: String = "",
    val error: String? = null,
    var isValid: Boolean = false
)