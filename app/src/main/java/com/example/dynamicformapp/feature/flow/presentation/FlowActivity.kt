package com.example.dynamicformapp.feature.flow.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.dynamicformapp.R
import com.example.dynamicformapp.databinding.ActivityFormBinding
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FormActivity : FragmentActivity(), FlowActions {

    private val viewModel: FlowViewModel by viewModels()

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            listen()
        }
    }

    override fun onNext() {
        viewModel.onNext()
    }

    override fun onPrevious() {
        viewModel.onPrevious()
    }

    override fun getFlows() {
        viewModel.getFlows()
    }

    override fun remove(vararg idSet: String) {
        viewModel.remove(*idSet)
    }

    private fun addStep(vo: StepVO) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.framelayout_flow, vo.view)
            .commit()
    }

    private fun removeStep(vo: StepVO) {
        supportFragmentManager
            .beginTransaction()
            .remove(vo.view)
            .commit()
    }

    @Deprecated("Deprecated in Java", ReplaceWith("onPrevious()"))
    override fun onBackPressed() {
        onPrevious()
    }

    private suspend fun listen() {
        viewModel.state.collect { state ->
            when (state) {
                is FlowViewModel.FlowState.AddStep -> addStep(state.vo)
                is FlowViewModel.FlowState.RemoveStep -> removeStep(state.vo)
                else -> {}
            }
        }
    }
}