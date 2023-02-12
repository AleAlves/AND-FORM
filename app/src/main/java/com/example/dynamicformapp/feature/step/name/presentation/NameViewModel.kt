package com.example.dynamicformapp.feature.step.name.presentation


import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.name.domain.NameFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val nameFormUseCase: NameFormUseCase
) : FormViewModel() {

    private var name = ""

    override fun loadForms() {
        initForms(nameFormUseCase(::onOutput))
    }

    override fun setupValidations() {
        nameFormUseCase.onValidation { value, _, _ ->
            name = value
        }
    }

    override fun getValidations(): Boolean = nameFormUseCase.isValid
}