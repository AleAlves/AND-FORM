package com.example.dynamicformapp.feature.flow.presentation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.dynamicformapp.R
import com.example.dynamicformapp.databinding.ActivityFormBinding
import com.example.dynamicformapp.databinding.FragmentLoginBinding
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

    override fun getFlows() = viewModel.getFlows()

    override fun remove(id: String) {
        viewModel.remove(id)
    }

    private fun onGoToStep() {

    }

    private fun onLoadSteps(steps: List<StepVO>) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framelayout_flow, steps.first().view)
            .commit()
    }

    private fun onRemoveSteps(steps: List<StepVO>) {
    }

    private fun onRemoveAt(position: Int) {
    }

    private fun onUpdate(steps: List<StepVO>) {

    }

    private suspend fun listen() {
        viewModel.state.collect { state ->
            when (state) {
                is FlowViewModel.FlowState.OnRemoveStepAt -> onRemoveAt(state.position)
                is FlowViewModel.FlowState.OnForwardStep -> onGoToStep()
                is FlowViewModel.FlowState.OnBackwardStep -> onGoToStep()
                is FlowViewModel.FlowState.OnLoadSteps -> onLoadSteps(state.steps)
                is FlowViewModel.FlowState.OnRemoveSteps -> onRemoveSteps(state.steps)
                is FlowViewModel.FlowState.OnUpdate -> onUpdate(state.steps)
                else -> {}
            }
        }
    }
}

inline fun <reified T : ViewBinding> Activity.viewBiding(
    crossinline inflater: (LayoutInflater) -> T
) = lazy {
    inflater(this.layoutInflater)
}

inline fun <reified T : ViewBinding> Fragment.viewBiding(
    crossinline inflater: (LayoutInflater) -> T
) = lazy {
    inflater(this.layoutInflater)
}