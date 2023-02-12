package com.example.dynamicformapp.feature.step.address.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dynamicformapp.databinding.FragmentAddressDetailBinding
import com.example.dynamicformapp.feature.form.presentation.ui.FormFragment
import com.example.dynamicformapp.feature.step.address.presentation.AddressDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddressDetailFragment : FormFragment() {

    private val viewModel: AddressDetailViewModel by viewModels()

    private lateinit var binding: FragmentAddressDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressDetailBinding.inflate(inflater, container, false)
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
        fun newInstance() = AddressDetailFragment()
    }
}