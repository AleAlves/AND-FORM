package com.example.dynamicformapp.feature.step.role.presentation


import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.role.domain.RoleFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoleViewModel @Inject constructor(
    private val roleForm: RoleFormUseCase
) : FormViewModel() {

    private var role = ""

    override fun onLoadForms() {
        initForms(*roleForm(::onOutput))
    }

    override fun onValidations() {
        roleForm.onValidation { value, _, _ ->
            role = value
        }
    }

    override fun getValidations(): Boolean = roleForm.isValid()
}