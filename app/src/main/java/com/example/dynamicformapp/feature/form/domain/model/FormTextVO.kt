package com.example.dynamicformapp.feature.form.domain.model

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
    val validation: FormValidation,
    override val onInput: ((FormData) -> Unit)
) : FormVO()

data class FormValidation(
    var rules: List<FormRuleSet>,
    var hasErrors: Boolean = false,
    val onRuleCallback: (FormValidation) -> Unit
)

data class FormRuleSet(
    val regex: Regex,
    val text: String = "",
    val error: String? = null,
    var isValid: Boolean = false,
)