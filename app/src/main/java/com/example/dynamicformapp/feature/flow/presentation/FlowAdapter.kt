package com.example.dynamicformapp.feature.flow.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dynamicformapp.feature.flow.domain.model.StepVO


class FlowAdapter(fragmentManager: FragmentActivity) : FragmentStateAdapter(fragmentManager) {

    private var step: ArrayList<StepVO> = ArrayList()

    fun addAll(vo: List<StepVO>) {
        step.addAll(vo)
    }

    override fun getItemCount(): Int {
        return step.size
    }

    override fun createFragment(position: Int): Fragment {
        return step[position].view
    }

    fun removeAt(position: Int) {
        step.apply {
            removeAt(position)
        }
    }

    fun removeAll(vo: List<StepVO>) {
        step.apply {
            removeAll(vo.toSet())
        }
    }
}