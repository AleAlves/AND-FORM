package com.example.dynamicformapp.feature.flow.presentation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.dynamicformapp.R
import com.example.dynamicformapp.feature.flow.domain.model.StepVO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormActivity : FragmentActivity(), FlowActions {

    private lateinit var mPager: ViewPager2
    private val pagerAdapter = FlowAdapter(this)

    private val viewModel: FlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        listen()
        setupPager()
    }

    private fun setupPager() {
        mPager = findViewById(R.id.view_pager_flow)
        mPager.apply {
            isUserInputEnabled = false
            adapter = pagerAdapter
        }
    }

    private fun listen() {
        viewModel.viewState.observe(this) { state ->
            when (state) {
                is FlowViewModel.FlowState.OnRemoveStepAt -> onRemoveAt(state.position)
                is FlowViewModel.FlowState.OnForwardStep -> onGoToStep(state.position)
                is FlowViewModel.FlowState.OnBackwardStep -> onGoToStep(state.position)
                is FlowViewModel.FlowState.OnLoadSteps -> onLoadSteps(state.steps)
                is FlowViewModel.FlowState.OnRemoveSteps -> onRemoveSteps(state.steps)
                is FlowViewModel.FlowState.OnUpdate -> onUpdate(state.steps)
            }
        }
    }

    override fun onNext() {
        viewModel.onNext(mPager.currentItem.plus(1))
    }

    override fun onPrevious() {
        viewModel.onPrevious(mPager.currentItem.minus(1))
    }

    override fun getFlows() = viewModel.getFlows()

    override fun remove(id: String) {
        viewModel.remove(id)
    }

    private fun onGoToStep(position: Int) {
        mPager.apply {
            currentItem = position
        }
    }

    private fun onLoadSteps(steps: List<StepVO>) {
        pagerAdapter.apply {
            addAll(steps)
            notifyItemRangeInserted(1, steps.size)
        }.run {
            mPager.adapter?.notifyItemRangeChanged(1, steps.size)
            onNext()
        }
    }

    private fun onRemoveSteps(steps: List<StepVO>) {
        pagerAdapter.apply {
            removeAll(steps)
            notifyItemRangeRemoved(0, steps.size)
        }.run {
            mPager.adapter?.notifyItemRangeRemoved(0, steps.size)
        }
    }

    private fun onRemoveAt(position: Int) {
        pagerAdapter.apply {
            removeAt(position)
            notifyItemRemoved(position)
        }.run {
            mPager.adapter?.notifyItemRemoved(position)
        }
    }

    private fun onUpdate(steps: List<StepVO>) {
        pagerAdapter.apply {
            addAll(steps)
            notifyItemRangeChanged(0, itemCount)
        }.run {
            notifyItemRangeChanged(0, mPager.size)
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