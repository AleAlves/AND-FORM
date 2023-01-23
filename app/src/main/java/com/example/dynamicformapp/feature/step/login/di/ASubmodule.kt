package com.example.dynamicformapp.feature.step.login.di

import com.example.dynamicformapp.feature.step.login.domain.StepAInteractor
import com.example.dynamicformapp.feature.step.login.domain.StepAInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ASubmodule {

    @Binds
    abstract fun bindsInteractor(interactor: StepAInteractorImpl): StepAInteractor
}