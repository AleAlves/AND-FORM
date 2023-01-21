package com.example.dynamicformapp.feature.flow.data.model

import com.example.dynamicformapp.feature.flow.presentation.StepFragment

data class FormModel(
    val flows: List<FlowModel>
)

data class FlowModel(
    val id: String,
    val steps: List<StepModel>
)

data class StepModel(
    val id: String,
    val returnable: Boolean,
    val view: StepFragment
)