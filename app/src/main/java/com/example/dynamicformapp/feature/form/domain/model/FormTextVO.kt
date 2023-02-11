package com.example.dynamicformapp.feature.form.domain.model

data class FormTextVO(
    var requestFocus: Boolean,
    val inputType: Int,
    val maxSize: Int,
    val minSize: Int,
    val mask: String = "",
    var hint: String = "",
    val subtitle: String = "",
    val isSingleLine: Boolean,
    val hasCounter: Boolean = true,
    var error: String? = null,
    val checkBox: FormCheckVO? = null,
    val ruleSet: FormRuleSet? = null,
    override var text: String = "",
    override var isEnabled: Boolean = true,
    override var isReadOnly: Boolean = false,
    override var fill: Boolean = true,
    override var gridSpan: Int = 3,
    override val onInput: ((FormIO) -> Unit),
) : FormVO()

data class FormRuleSet(
    var rules: List<FormRule>,
    var hasErrors: Boolean = false,
    val onRuleCallback: (FormRuleSet) -> Unit
)

data class FormRule(
    val regex: Regex,
    val name: String = "",
    val error: String? = null,
    var isValid: Boolean = false
)