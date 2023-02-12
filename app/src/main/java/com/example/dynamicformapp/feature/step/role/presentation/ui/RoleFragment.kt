package com.example.dynamicformapp.feature.step.role.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dynamicformapp.databinding.FragmentRoleBinding
import com.example.dynamicformapp.feature.form.presentation.ui.FormFragment
import com.example.dynamicformapp.feature.step.role.presentation.RoleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoleFragment : FormFragment() {

    private val viewModel: RoleViewModel by viewModels()

    private lateinit var binding: FragmentRoleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoleBinding.inflate(inflater, container, false)
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
        fun newInstance() = RoleFragment()
    }
}