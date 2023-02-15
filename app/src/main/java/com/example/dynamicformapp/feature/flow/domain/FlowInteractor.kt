package com.example.dynamicformapp.feature.flow.domain

import com.example.dynamicformapp.feature.flow.data.FlowRepository
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import com.example.dynamicformapp.feature.step.cpf.presentation.ui.CpfFragment
import com.example.dynamicformapp.feature.step.password.presentation.ui.PasswordFragment
import com.example.dynamicformapp.feature.step.address.presentation.ui.AddressDetailFragment
import com.example.dynamicformapp.feature.step.address.presentation.ui.AddressZipCodeFragment
import com.example.dynamicformapp.feature.step.name.presentation.ui.NameFragment
import com.example.dynamicformapp.feature.step.phone.presentation.ui.PhoneFragment
import com.example.dynamicformapp.feature.step.role.presentation.ui.RoleFragment
import javax.inject.Inject

interface FlowInteractor {
    fun getStartupStep(): List<StepVO>
    fun fetchFormSteps(): List<StepVO>
}

class FlowInteractorImpl @Inject constructor(
    private val repository: FlowRepository
) : FlowInteractor {

    override fun getStartupStep(): List<StepVO> {
        return listOf(StepVO(id = "CPF", true, CpfFragment.newInstance()))
    }

    override fun fetchFormSteps() = listOf(
        StepVO(id = "NAME", true, NameFragment.newInstance()),
        StepVO(id = "ROLE", true, RoleFragment.newInstance()),
        StepVO(id = "PHONE", true, PhoneFragment.newInstance()),
        StepVO(id = "ADDRESS", true, AddressZipCodeFragment.newInstance()),
        StepVO(id = "ADDRESS", true, AddressDetailFragment.newInstance()),
        StepVO(id = "PASSWORD", true, PasswordFragment.newInstance()),
    )
}