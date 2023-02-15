package com.example.dynamicformapp.feature.flow.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.viewModels
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

    private fun addStep(vo: StepVO) = updateStep(vo)

    private fun removeStep(vo: StepVO) = updateStep(vo)

    private fun updateStep(vo: StepVO) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framelayout_flow, vo.step, vo.id)
            .addToBackStack(vo.id)
            .commit()
    }


    @Deprecated("Deprecated in Java", ReplaceWith("onPrevious()"))
    override fun onBackPressed() {
        onPreviousStep()
    }

    private fun listen() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is FlowViewModel.FlowState.Steps.AddStep -> addStep(state.vo)
                is FlowViewModel.FlowState.Steps.RemoveStep -> removeStep(state.vo)
                is FlowViewModel.FlowState.Progress.OnUpdate -> {
                    ObjectAnimator.ofInt(
                        binding.inputProgress,
                        "progress",
                        binding.inputProgress.progress,
                        state.progress
                    ).apply {
                        duration = 1000L
                        start()
                    }
                }
            }
        }
    }
}