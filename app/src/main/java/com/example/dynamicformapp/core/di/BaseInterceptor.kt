package com.example.dynamicformapp.core.di

import com.example.dynamicformapp.core.model.BaseResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class BaseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            throw Exception()
        }
        val type = response.body()?.contentType()
        val body = response.body().toString()
        val data = Gson().fromJson(body, BaseResponse::class.java).data
        return response.newBuilder().apply {
            body(ResponseBody.create(type, data))
        }.build()
    }
}