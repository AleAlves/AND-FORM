package com.example.dynamicformapp.feature.step.address.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dynamicformapp.databinding.FragmentAddressZipBinding
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import com.example.dynamicformapp.feature.form.domain.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.FormFragment
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.address.presentation.AddressZipCodeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddressZipCodeFragment : FormFragment() {

    private val viewModel: AddressZipCodeViewModel by viewModels()

    private lateinit var binding: FragmentAddressZipBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressZipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupView() {
        with(binding.inputContainer) {
            buttonNext.setOnClickListener {
                super.getFlows()
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
        fun newInstance() = AddressZipCodeFragment()
    }
}