package com.example.dynamicformapp.feature.step.login.presentation


import android.graphics.Paint
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
                is LoginViewModel.LoginState.OnPasswordLengthRule -> onPasswordLengthRule(it.isDone)
                is LoginViewModel.LoginState.OnPasswordCharactersRule -> onPasswordCharsRule(it.isDone)
            }
        }
    }

    private fun onPasswordLengthRule(isDone: Boolean) {
        binding.textviewPasswordRuleOne.text = "Tamanho minimo de 6 caracteres"
        if (isDone) {
            binding.textviewPasswordRuleOne.paintFlags =
                binding.textviewPasswordRuleOne.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.textviewPasswordRuleOne.paintFlags = Paint.LINEAR_TEXT_FLAG
        }
    }

    private fun onPasswordCharsRule(isDone: Boolean) {
        binding.textviewPasswordRuleTwo.text = "Deve ter letras e números"
        if (isDone) {
            binding.textviewPasswordRuleTwo.paintFlags =
                binding.textviewPasswordRuleOne.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.textviewPasswordRuleTwo.paintFlags = Paint.LINEAR_TEXT_FLAG
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