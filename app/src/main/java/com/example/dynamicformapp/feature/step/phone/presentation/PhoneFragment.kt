package com.example.dynamicformapp.feature.step.phone.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dynamicformapp.R
import com.example.dynamicformapp.databinding.FragmentPhoneBinding
import com.example.dynamicformapp.databinding.FragmentRoleBinding
import com.example.dynamicformapp.feature.flow.presentation.StepFragment
import com.example.dynamicformapp.feature.form.domain.model.FormVO
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.password.presentation.PasswordViewModel
import com.example.dynamicformapp.feature.step.password.presentation.ui.PasswordFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhoneFragment : StepFragment() {

    private val viewModel: PhoneViewModel by viewModels()

    private lateinit var binding: FragmentPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.inputContainer.buttonNext.setOnClickListener {
            super.onNext()
        }
        lifecycleScope.launch { listenChanges() }
        binding.inputContainer.inputView.onInput = viewModel::onInput
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
        binding.inputContainer.buttonNext.isEnabled = isValid
    }

    private fun setFormData(forms: List<FormVO>) {
        binding.inputContainer.inputView.setData(forms)
    }

    private fun notifyOutputAt(position: Int) {
        binding.inputContainer.inputView.notifyChangeAt(position)
    }

    private fun updateForms() {
        binding.inputContainer.inputView.updateForm()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PhoneFragment()
    }
}