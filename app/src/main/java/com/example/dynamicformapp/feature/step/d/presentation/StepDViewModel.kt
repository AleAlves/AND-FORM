package com.example.dynamicformapp.feature.step.d.presentation

import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StepDViewModel @Inject constructor(
) : BaseViewModel<ViewState>() {
    override val initialState: FormViewModel.FormState = FormViewModel.FormState.Init
}