package com.example.dynamicformapp.feature.flow.di

import com.example.dynamicformapp.feature.flow.data.FlowRepository
import com.example.dynamicformapp.feature.flow.data.FlowRepositoryImpl
import com.example.dynamicformapp.feature.flow.data.FormService
import com.example.dynamicformapp.feature.flow.domain.FlowInteractor
import com.example.dynamicformapp.feature.flow.domain.FlowInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class FormModule {

    @Binds
    abstract fun bindFlowRepository(repository: FlowRepositoryImpl): FlowRepository

    @Binds
    abstract fun bindFlowInteractor(interactor: FlowInteractorImpl): FlowInteractor

    companion object {

        @Provides
        fun providePicPayService(retrofit: Retrofit): FormService {
            return retrofit.create(FormService::class.java)
        }
    }
}