package com.example.dynamicformapp.feature.step.a.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dynamicformapp.databinding.FragmentStepABinding
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StepAFragment : StepFragment() {

    private val viewModel: StepAViewModel by viewModels()

    private lateinit var binding: FragmentStepABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonApi.setOnClickListener {
            viewModel.doLogin()
            super.getFlows()
        }
        binding.inputView.onReadInput = viewModel::onFormInput
        listenChanges()
    }

    private fun listenChanges() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is StepAViewModel.StepAState.OnLoad ->
                    binding.inputView.setData(it.forms)
                is StepAViewModel.StepAState.OnButtonStatus ->
                    binding.buttonApi.isEnabled = it.isEnabled
                is StepAViewModel.StepAState.OnUpdate -> binding.inputView.post {
                    binding.inputView.notifyChangeAt(it.position)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = StepAFragment()
    }
}