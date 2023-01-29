package com.example.dynamicformapp.feature.step.login.presentation

import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.login.domain.StepAInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val interactor: StepAInteractor
) : FormViewModel(interactor) {

    fun doLogin() = interactor.doLoginCall()
}