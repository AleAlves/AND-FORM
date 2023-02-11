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
import com.example.dynamicformapp.feature.form.presentation.FormFragment
import com.example.dynamicformapp.feature.form.presentation.FormViewModel
import com.example.dynamicformapp.feature.step.password.presentation.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordFragment : FormFragment() {

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
        lifecycleScope.launch { listenChanges() }
    }

    private suspend fun listenChanges() {
        viewModel.state.collect {
            when (it) {
                is PasswordViewModel.PasswordState.LoadRules -> loadRules(it.rules)
            }
        }
    }

    private fun loadRules(rulesSet: FormRuleSet?) {
        binding.rulesView.setData(rulesSet?.rules)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PasswordFragment()
    }
}