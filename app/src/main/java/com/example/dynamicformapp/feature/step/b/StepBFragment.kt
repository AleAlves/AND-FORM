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
class StepBFragment : StepFragment() {

    private val viewModel: StepBViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getName()

        view.findViewById<Button>(R.id.button_next).setOnClickListener {
            super.onNext()
        }
        view.findViewById<Button>(R.id.button_back).setOnClickListener {
            super.onPrevious()
        }
//        viewModel.state.observe(viewLifecycleOwner) {
//            when (it) {
//                is StepBViewModel.StepBState.OnLoadName -> {
//                    print("")
//                }
//            }
//        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = StepBFragment()
    }
}