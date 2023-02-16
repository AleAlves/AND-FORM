package com.example.dynamicformapp.feature.step.address.presentation


import androidx.lifecycle.viewModelScope
import com.example.dynamicformapp.feature.form.domain.GenericFormUseCase
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.address.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private var address = ""
    private var noNumber = false

    override fun loadForms() {
        viewModelScope.launch {
            initForms(
                cityFormUseCase(::onOutput),
                stateUseCase(::onOutput),
                sateAcronymFormUseCase(::onOutput),
                addressForm(::onOutput),
                addressNumberFormUseCase(::onOutput),
                addressComplementFormUseCase(::onOutput)
            )
        }
    }

    override fun onSetupForms() {
        super.onSetupForms()
        cityFormUseCase.formVO.text = "São Paulo"
        stateUseCase.formVO.text = "São Paulo"
        sateAcronymFormUseCase.formVO.text = "SP"
    }

    override fun setupValidations() {
        addressForm.onValidation { value, _, _ ->
            address = value
        }

        with(addressNumberFormUseCase) {
            onValidation { _, isSelected, _ ->
                updateFormFields {
                    with(formVO) {
                        isEnabled = isSelected.not()
                        noNumber = isSelected
                    }
                }
            }
        }
    }

    override fun getValidations(): Boolean =
        addressForm.isValid.and(addressNumberFormUseCase.isValid || noNumber)
}