package com.example.dynamicformapp.feature.form.presentation.ui


import android.os.Bundle
import android.widget.Button
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import com.example.dynamicformapp.feature.form.domain.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class FormFragment : StepFragment() {

    private lateinit var inputView: FormInputView
    private lateinit var buttonNext: Button
    private lateinit var viewModel: FormViewModel

    abstract fun onSetupView()

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        onSetupView()
        viewModel.onLoadForms()
    }


    fun setupFormView(
        inputView: FormInputView,
        buttonNext: Button,
        viewModel: FormViewModel
    ) {
        this.viewModel = viewModel
        this.inputView = inputView
        this.buttonNext = buttonNext
        inputView.onInput = viewModel::onInput
        listenChanges()
    }


    private fun listenChanges() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is FormViewModel.FormState.OnLoadForm -> setFormData(it.forms)
                is FormViewModel.FormState.Field.OnFieldOutput -> notifyOutputAt(it.position)
                is FormViewModel.FormState.Field.OnUpdateFields -> updateForms()
                is FormViewModel.FormState.Button.OnValidation -> buttonValidationToggle(it.isValid)
            }
        }
    }

    private fun buttonValidationToggle(isValid: Boolean) {
        buttonNext.isEnabled = isValid
    }

    private fun setFormData(forms: List<FormVO>) {
        inputView.setData(forms)
    }

    private fun notifyOutputAt(position: Int) {
        inputView.notifyChangeAt(position)
    }

    private fun updateForms() {
        inputView.updateForm()
    }

    private fun onSoftInput(shouldShow: Boolean) {

    }
}