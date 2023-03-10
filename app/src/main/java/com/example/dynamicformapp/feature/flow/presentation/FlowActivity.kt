package com.example.dynamicformapp.feature.flow.presentation

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.*
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.example.dynamicformapp.R
import com.example.dynamicformapp.databinding.ActivityFormBinding
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
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
        viewModel.getFlows()
    }

    override fun onNextStep() {
        viewModel.onNext()
    }

    override fun onPreviousStep() {
        viewModel.onPrevious()
    }

    override fun remove(vararg idSet: String) {
        viewModel.remove(*idSet)
    }

    private fun updateStep(vo: StepVO) = with(supportFragmentManager) {
        with(vo) {
            beginTransaction().replace(
                R.id.framelayout_flow, findFragmentByTag(id) ?: fragment.newInstance(), id
            ).addToBackStack(id).commit()
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("onPrevious()"))
    override fun onBackPressed() {
        onPreviousStep()
    }

    private fun listen() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is FlowViewModel.FlowState.Steps.ToNext -> updateStep(state.vo)
                is FlowViewModel.FlowState.Steps.ToPrevious -> updateStep(state.vo)
                is FlowViewModel.FlowState.Steps.Finish -> finish()
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