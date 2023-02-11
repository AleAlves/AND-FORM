package com.example.dynamicformapp.feature.step.password.presentation.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dynamicformapp.databinding.FragmentPasswordBinding
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import com.example.dynamicformapp.feature.form.domain.model.FormRuleSet
import com.example.dynamicformapp.feature.form.domain.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.password.presentation.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordFragment : StepFragment() {

    private val viewModel: PasswordViewModel by viewModels()

    private lateinit var binding: FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.buttonNext.setOnClickListener {
            super.onNext()
        }
        binding.buttonBack.setOnClickListener {
            super.onPrevious()
        }
        lifecycleScope.launch { listenChanges() }
        binding.inputView.onInput = viewModel::onInput
        viewModel.loadForms()
    }

    private suspend fun listenChanges() {
        viewModel.state.collect {
            when (it) {
                is FormViewModel.FormState.OnInitForms -> setFormData(it.forms)
                is FormViewModel.FormState.OnFormOutput -> notifyOutputAt(it.position)
                is FormViewModel.FormState.OnValidation -> buttonValidationToggle(it.isValid)
                is FormViewModel.FormState.OnUpdatingForms -> updateForms()
                is PasswordViewModel.PasswordState.LoadRules -> loadRules(it.rules)
            }
        }
    }

    private fun loadRules(rulesSet: FormRuleSet?) {
        binding.rulesView.setData(rulesSet?.rules)
    }

    private fun buttonValidationToggle(isValid: Boolean) {
        binding.buttonNext.isEnabled = isValid
    }

    private fun setFormData(forms: List<FormVO>) {
        binding.inputView.setData(forms)
    }

    private fun notifyOutputAt(position: Int) {
        binding.inputView.notifyChangeAt(position)
    }

    private fun updateForms() {
        binding.inputView.updateForm()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PasswordFragment()
    }
}