package com.example.dynamicformapp.feature.step.d.presentation


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
class StepDFragment : StepFragment() {

    private val viewModel: StepDViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_back).setOnClickListener {
            super.onPrevious()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = StepDFragment()
    }
}