package com.example.dynamicformapp.feature.flow.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.dynamicformapp.R
import com.example.dynamicformapp.databinding.ActivityFormBinding
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormActivity : FragmentActivity(), FlowActions {

    private val viewModel: FlowViewModel by viewModels()

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listen()
    }

    override fun onNextStep() {
        viewModel.onNext()
    }

    override fun onPreviousStep() {
        viewModel.onPrevious()
    }

    override fun getSteps() {
        viewModel.getFlows()
    }

    override fun remove(vararg idSet: String) {
        viewModel.remove(*idSet)
    }

    private fun addStep(vo: StepVO) {
        supportFragmentManager.beginTransaction().add(R.id.framelayout_flow, vo.step).commit()
    }

    private fun removeStep(vo: StepVO) {
        with(supportFragmentManager) {
            beginTransaction().remove(fragments.last()).commit()
        }
    }


    @Deprecated("Deprecated in Java", ReplaceWith("onPrevious()"))
    override fun onBackPressed() {
        onPreviousStep()
    }

    private fun listen() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is FlowViewModel.FlowState.AddStep -> addStep(state.vo)
                is FlowViewModel.FlowState.RemoveStep -> removeStep(state.vo)
            }
        }
    }
}