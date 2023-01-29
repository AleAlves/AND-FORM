package com.example.dynamicformapp.feature.step.login.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dynamicformapp.databinding.FragmentLoginBinding
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import com.example.dynamicformapp.feature.form.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : StepFragment() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
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
                is FormViewModel.FormState.OnValidation -> buttonValidationToggle(it.isEnabled)
                is FormViewModel.FormState.OnInitForms -> setFormData(it.forms)
                is FormViewModel.FormState.OnFormOutput -> notifyOutputAt(it.position)
            }
        }
    }

    private fun buttonValidationToggle(enabled: Boolean) {
        binding.buttonApi.isEnabled = enabled
    }

    private fun setFormData(forms: List<FormVO>) {
        binding.inputView.setData(forms)
    }

    private fun notifyOutputAt(position: Int) {
        binding.inputView.notifyChangeAt(position)
    }

    companion

    object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}