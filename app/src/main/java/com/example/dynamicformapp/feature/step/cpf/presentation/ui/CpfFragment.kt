package com.example.dynamicformapp.feature.step.cpf.presentation.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dynamicformapp.databinding.FragmentCpfBinding
import com.example.dynamicformapp.feature.form.presentation.ui.FormFragment
import com.example.dynamicformapp.feature.step.cpf.presentation.CpfViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CpfFragment : FormFragment() {

    private val viewModel: CpfViewModel by viewModels()

    private lateinit var binding: FragmentCpfBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCpfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupView() {
        with(binding.inputContainer) {
            buttonNext.setOnClickListener {
                super.getSteps()
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
        fun newInstance() = CpfFragment()
    }
}