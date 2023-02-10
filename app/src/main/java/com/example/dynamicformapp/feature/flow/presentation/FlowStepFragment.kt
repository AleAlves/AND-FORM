package com.example.dynamicformapp.feature.flow.presentation

import android.content.Context
import androidx.fragment.app.Fragment

interface FlowActions {
    fun onNext()
    fun onPrevious()
    fun getFlows()
    fun remove(id: String)
}

abstract class StepFragment : Fragment(), FlowActions {

    private lateinit var actions: FlowActions

    override fun onNext() = actions.onNext()

    override fun onPrevious() = actions.onPrevious()

    override fun getFlows() = actions.getFlows()

    override fun remove(id: String) = actions.remove(id)

    override fun onAttach(context: Context) {
        actions = context as FlowActions
        super.onAttach(context)
    }
}