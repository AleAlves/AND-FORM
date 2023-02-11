package com.example.dynamicformapp.feature.step.cpf.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dynamicformapp.databinding.FragmentCpfBinding
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import com.example.dynamicformapp.feature.form.domain.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.FormFragment
import com.example.dynamicformapp.feature.form.presentation.FormInputView
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        fun newInstance() = CpfFragment()
    }
}