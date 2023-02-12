package com.example.dynamicformapp.feature.flow.presentation

import android.content.Context
import androidx.fragment.app.Fragment

interface FlowActions {
    fun onNextStep()
    fun onPreviousStep()
    fun getSteps()
    fun remove(vararg idSet: String)
}

abstract class StepFragment : Fragment(), FlowActions {

    private lateinit var actions: FlowActions

    override fun getSteps() = actions.getSteps()

    override fun onNextStep() = actions.onNextStep()

    override fun onPreviousStep() = actions.onPreviousStep()

    override fun remove(vararg idSet: String) = actions.remove(*idSet)

    override fun onAttach(context: Context) {
        actions = context as FlowActions
        super.onAttach(context)
    }
}