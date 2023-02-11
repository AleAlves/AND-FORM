package com.example.dynamicformapp.feature.flow.domain

import com.example.dynamicformapp.feature.flow.data.FlowRepository
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import com.example.dynamicformapp.feature.step.cpf.presentation.CpfFragment
import com.example.dynamicformapp.feature.step.password.presentation.ui.PasswordFragment
import com.example.dynamicformapp.feature.step.address.presentation.ui.AddressDetailFragment
import com.example.dynamicformapp.feature.step.address.presentation.ui.AddressZipCodeFragment
import com.example.dynamicformapp.feature.step.name.presentation.NameFragment
import com.example.dynamicformapp.feature.step.phone.presentation.PhoneFragment
import com.example.dynamicformapp.feature.step.role.presentation.RoleFragment
import javax.inject.Inject

interface FlowInteractor {
    fun getStartupStep(): List<StepVO>
    fun fetchFormSteps(): List<StepVO>
}

class FlowInteractorImpl @Inject constructor(
    private val repository: FlowRepository
) : FlowInteractor {

    override fun getStartupStep(): List<StepVO> {
        return listOf(StepVO(id = "Cpf", flowId = "Cpf", true, CpfFragment.newInstance()))
    }

    override fun fetchFormSteps() = listOf(
        StepVO(id = "Name", flowId = "Name", true, NameFragment.newInstance()),
        StepVO(id = "Role", flowId = "Role", true, RoleFragment.newInstance()),
        StepVO(id = "Phone", flowId = "Phone", true, PhoneFragment.newInstance()),
        StepVO(id = "AddressZip", flowId = "Address", true, AddressZipCodeFragment.newInstance()),
        StepVO(id = "AddressDetail", flowId = "Address", true, AddressDetailFragment.newInstance()),
        StepVO(id = "Password", flowId = "Password", true, PasswordFragment.newInstance()),
    )
}