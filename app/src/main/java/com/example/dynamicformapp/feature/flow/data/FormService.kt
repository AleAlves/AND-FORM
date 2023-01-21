package com.example.dynamicformapp.feature.flow.data

import com.example.dynamicformapp.feature.flow.data.model.FormResponse
import retrofit2.Call
import retrofit2.http.GET


interface FormService {

    @GET("api/forms")
    fun getForms(): Call<Response>
}

data class Response(val data: List<FormResponse>)