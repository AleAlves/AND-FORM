package com.example.dynamicformapp.core

import android.app.Application
import android.text.Editable
import android.util.Log
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CustomMainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppSignatureHashHelper(this).appSignatures
    }
}
