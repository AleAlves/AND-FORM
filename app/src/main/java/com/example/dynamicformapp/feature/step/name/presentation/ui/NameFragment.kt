package com.example.dynamicformapp.feature.step.name.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dynamicformapp.databinding.FragmentNameBinding
import com.example.dynamicformapp.feature.form.presentation.ui.FormFragment
import com.example.dynamicformapp.feature.step.name.presentation.NameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameFragment : FormFragment() {

    private val viewModel: NameViewModel by viewModels()

    private lateinit var binding: FragmentNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupView() {
        with(binding.inputContainer) {
            buttonNext.setOnClickListener {
                super.onNextStep()
            }
            setupFormView(
                inputView,
                buttonNext,
                viewModel
            )
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = NameFragment()
    }
}