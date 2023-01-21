package com.example.dynamicformapp.feature.flow.data

import com.example.dynamicformapp.feature.flow.data.model.FormResponse
import javax.inject.Inject

interface FlowRepository {
    fun fetchFlows(): List<FormResponse>
}

class FlowRepositoryImpl @Inject constructor(
    private val service: FormService
) : FlowRepository {

    override fun fetchFlows(): List<FormResponse> {
        val service = service.getForms().execute()
        return service.body()?.data as List<FormResponse>
    }
}