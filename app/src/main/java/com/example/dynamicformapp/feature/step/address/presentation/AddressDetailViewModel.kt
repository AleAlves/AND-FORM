package com.example.dynamicformapp.feature.step.address.presentation


import com.example.dynamicformapp.feature.form.domain.GenericFormUseCase
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.address.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressDetailViewModel @Inject constructor(
    private val complementForm: AddressComplementFormUseCase,
    private val numberForm: AddressNumberFormUseCase,
    private val acronymForm: GenericFormUseCase,
    private val cityForm: GenericFormUseCase,
    private val stateUseCase: GenericFormUseCase,
    private val addressForm: AddressFormUseCase
) : FormViewModel() {

    private var address = ""
    private var noNumber = false

    override fun onLoadForms() {
        initForms(
            cityForm(::onOutput),
            stateUseCase(::onOutput),
            acronymForm(::onOutput),
            addressForm(::onOutput),
            numberForm(::onOutput),
            complementForm(::onOutput)
        )
    }

    override fun onSetupForms() {
        cityForm.formVO.text = "São Paulo"
        stateUseCase.formVO.text = "São Paulo"
        acronymForm.formVO.text = "SP"
    }

    override fun onValidations() {
        addressForm.onValidation { value, _, _ ->
            address = value
        }
        numberForm.onValidation { _, isSelected, _ ->
            noNumber = isSelected
            update(isSelected)
        }
    }

    private fun update(isSelected: Boolean) {
        updateFormFields {
            if (isSelected) {
                numberForm.formVO.text = ""
                complementForm.formVO.hint = "Complement"
            } else {
                numberForm.formVO.hint = "Number"
                complementForm.formVO.hint = "Complement (Optional)"
            }
            numberForm.formVO.isEnabled = isSelected.not()
        }
    }

    override fun getValidations(): Boolean =
        addressForm.isValid()
            .and(numberForm.isValid() || noNumber)
}