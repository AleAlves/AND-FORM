package com.example.dynamicformapp.feature.step.b

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.example.dynamicformapp.R
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepB3Fragment : StepFragment() {

    private val viewModel: StepBViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step_b_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_next).setOnClickListener {
            super.onNext()
        }
        view.findViewById<Button>(R.id.button_back).setOnClickListener {
            super.onPrevious()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = StepB3Fragment()
    }
}