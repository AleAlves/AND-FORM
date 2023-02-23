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
    fun fetchFormSteps(): List<StepVO>
}

class FlowInteractorImpl @Inject constructor(
    private val repository: FlowRepository
) : FlowInteractor {

    override fun fetchFormSteps() = listOf(
        StepVO(id = "ID", true, CpfFragment::class.java),
        StepVO(id = "NAME", true, NameFragment::class.java),
        StepVO(id = "ROLE", true, RoleFragment::class.java),
        StepVO(id = "PHONE", true, PhoneFragment::class.java),
        StepVO(id = "ADDRESS_ZIP", true, AddressZipCodeFragment::class.java),
        StepVO(id = "ADDRESS_DETAIL", false, AddressDetailFragment::class.java),
        StepVO(id = "PASSWORD", true, PasswordFragment::class.java),
    )
}