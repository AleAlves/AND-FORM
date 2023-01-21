package com.example.dynamicformapp.core

import android.app.Application
import android.text.Editable
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CustomMainApplication : Application()

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
