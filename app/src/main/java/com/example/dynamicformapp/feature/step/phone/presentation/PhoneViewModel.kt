package com.example.dynamicformapp.feature.step.phone.presentation


import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.phone.domain.PhoneFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor(
    private val phoneFormUseCase: PhoneFormUseCase
) : FormViewModel() {

    private var role = ""

    override val initialState: FormState = FormState.Init

    override fun loadForms() {
        initForms(
            phoneFormUseCase(::onOutput),
        )
    }

    override fun setupValidations() {
        phoneFormUseCase.onValidation { value, _, _ ->
            role = value
        }
    }

    override fun getValidations(): Boolean = phoneFormUseCase.isValid
}