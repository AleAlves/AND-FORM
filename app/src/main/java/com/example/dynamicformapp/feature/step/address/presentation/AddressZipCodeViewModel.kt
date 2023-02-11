package com.example.dynamicformapp.feature.step.address.presentation


import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.address.domain.AddressFormUseCase
import com.example.dynamicformapp.feature.step.address.domain.ZipCodeFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressZipCodeViewModel @Inject constructor(
    private val zipCodeForm: ZipCodeFormUseCase
) : FormViewModel() {

    override val initialState: FormState = FormState.Init

    private var zipCode = ""

    override fun loadForms() {
        initForms(
            zipCodeForm(::onOutput)
        )
    }

    override fun setupValidations() {
        zipCodeForm.onValidation { value, _, _ ->
            zipCode = value
        }
    }


    override fun getValidations(): Boolean = zipCodeForm.isValid
}