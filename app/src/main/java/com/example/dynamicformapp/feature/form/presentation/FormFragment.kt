package com.example.dynamicformapp.feature.form.presentation


import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import com.example.dynamicformapp.feature.form.domain.model.FormVO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class FormFragment : StepFragment() {

    private lateinit var inputView: FormInputView
    private lateinit var buttonNext: Button
    private lateinit var viewModel: FormViewModel

    fun setupFormView(
        inputView: FormInputView,
        buttonNext: Button,
        viewModel: FormViewModel
    ) {
        this.inputView = inputView
        this.buttonNext = buttonNext
        this.viewModel = viewModel
        lifecycleScope.launch { listenChanges() }
        setupView()
    }

    private fun setupView() {
        inputView.onInput = viewModel::onInput
        viewModel.loadForms()
    }


    private suspend fun listenChanges() {
        viewModel.state.collect {
            when (it) {
                is FormViewModel.FormState.OnInitForms -> setFormData(it.forms)
                is FormViewModel.FormState.OnFormOutput -> notifyOutputAt(it.position)
                is FormViewModel.FormState.OnValidation -> buttonValidationToggle(it.isValid)
                is FormViewModel.FormState.OnUpdatingForms -> updateForms()
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
}