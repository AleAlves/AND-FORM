package com.example.dynamicformapp.feature.step.role.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dynamicformapp.databinding.FragmentRoleBinding
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import com.example.dynamicformapp.feature.form.domain.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoleFragment : StepFragment() {

    private val viewModel: RoleViewModel by viewModels()

    private lateinit var binding: FragmentRoleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoleBinding.inflate(inflater, container, false)
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
            }
        }
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
        fun newInstance() = RoleFragment()
    }
}