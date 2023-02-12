package com.example.dynamicformapp.feature.step.phone.presentation.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dynamicformapp.databinding.FragmentPhoneBinding
import com.example.dynamicformapp.feature.form.presentation.ui.FormFragment
import com.example.dynamicformapp.feature.step.phone.presentation.PhoneViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneFragment : FormFragment() {

    private val viewModel: PhoneViewModel by viewModels()

    private lateinit var binding: FragmentPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhoneBinding.inflate(inflater, container, false)
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
        fun newInstance() = PhoneFragment()
    }
}