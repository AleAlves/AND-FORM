package com.example.dynamicformapp.feature.flow.domain

import com.example.dynamicformapp.feature.flow.data.FlowRepository
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import com.example.dynamicformapp.feature.step.a.presentation.StepAFragment
import com.example.dynamicformapp.feature.step.d.presentation.StepDFragment
import com.example.dynamicformapp.feature.step.b.StepB2Fragment
import com.example.dynamicformapp.feature.step.b.StepB3Fragment
import com.example.dynamicformapp.feature.step.b.StepBFragment
import com.example.dynamicformapp.feature.step.c.StepCFragment
import javax.inject.Inject

interface FlowInteractor {
    fun getStartupStep(): List<StepVO>
    fun fetchFormSteps(): List<StepVO>
    fun wow()
}

class FlowInteractorImpl @Inject constructor(
    private val repository: FlowRepository
) : FlowInteractor {

    override fun getStartupStep(): List<StepVO> {
        return listOf(StepVO(id = "A1", flowId = "A", true, StepAFragment.newInstance()))
    }

    override fun fetchFormSteps() = listOf(
        StepVO(id = "B1", flowId = "B", true, StepBFragment.newInstance()),
        StepVO(id = "B2", flowId = "B", true, StepB2Fragment.newInstance()),
        StepVO(id = "B3", flowId = "B", false, StepB3Fragment.newInstance()),
        StepVO(id = "C1", flowId = "C", true, StepCFragment.newInstance()),
        StepVO(id = "D1", flowId = "D", true, StepDFragment.newInstance()),
    )

    override fun wow() {
        var response = repository.fetchFlows()
        print("")
    }
}