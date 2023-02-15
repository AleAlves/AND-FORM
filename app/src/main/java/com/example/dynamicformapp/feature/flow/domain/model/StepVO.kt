package com.example.dynamicformapp.feature.flow.domain.model

import com.example.dynamicformapp.feature.form.presentation.ui.FormFragment


data class StepVO(
    val id: String,
    val returnable: Boolean,
    val fragment: Class<out FormFragment>
)