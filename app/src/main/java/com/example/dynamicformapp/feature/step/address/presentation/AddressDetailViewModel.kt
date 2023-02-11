package com.example.dynamicformapp.feature.step.address.presentation


import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.address.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressDetailViewModel @Inject constructor(
    private val addressComplementFormUseCase: AddressComplementFormUseCase,
    private val addressNumberFormUseCase: AddressNumberFormUseCase,
    private val sateAcronymFormUseCase: GenericFormUseCase,
    private val cityFormUseCase: GenericFormUseCase,
    private val stateUseCase: GenericFormUseCase,
    private val addressForm: AddressFormUseCase
) : FormViewModel() {

    override val initialState: FormState = FormState.Init

    private var address = ""
    private var noNumber = false

    override fun loadForms() {
        initForms(
            cityFormUseCase(::onOutput),
            stateUseCase(::onOutput),
            sateAcronymFormUseCase(::onOutput),
            addressForm(::onOutput),
            addressNumberFormUseCase(::onOutput),
            addressComplementFormUseCase(::onOutput)
        )
    }

    override fun onSetupForms() {
        cityFormUseCase.formVO.text = "São Paulo"
        stateUseCase.formVO.text = "São Paulo"
        sateAcronymFormUseCase.formVO.text = "SP"
    }

    override fun setupValidations() {
        addressForm.onValidation { value, _, _ ->
            address = value
        }

        addressNumberFormUseCase.onValidation { _, isSelected, _ ->
            updateFormFields {
                with(addressNumberFormUseCase.formVO) {
                    isEnabled = isSelected.not()
                    noNumber = isSelected
                }
            }
        }
    }

    override fun getValidations(): Boolean =
        addressForm.isValid.and(addressNumberFormUseCase.isValid || noNumber)
}