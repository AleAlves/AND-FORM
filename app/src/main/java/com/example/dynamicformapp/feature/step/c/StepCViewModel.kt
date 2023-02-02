package com.example.dynamicformapp.feature.step.c


import com.example.dynamicformapp.core.presentation.BaseViewModel
import com.example.dynamicformapp.core.presentation.ui.ViewState
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StepCViewModel @Inject constructor(

) : BaseViewModel<FormViewModel.FormState>() {
    override val initialState: FormViewModel.FormState = FormViewModel.FormState.Init
}