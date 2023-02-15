package com.example.dynamicformapp.feature.flow.domain.model

import com.example.dynamicformapp.feature.flow.presentation.StepFragment


data class StepVO(
    val id: String,
    val returnable: Boolean,
    val step: StepFragment
)