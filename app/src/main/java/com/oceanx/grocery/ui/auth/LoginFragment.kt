package com.oceanx.grocery.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.oceanx.grocery.R
import com.oceanx.grocery.databinding.FragmentLoginBinding
import com.oceanx.grocery.utils.shakeAnimation
import com.oceanx.grocery.utils.toast
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilPhone.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSendOtp.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            viewModel.validatePhone(phone)
        }

        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.OtpSent -> {
                        findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
                        viewModel.resetState()
                    }
                    is AuthState.PhoneError -> {
                        binding.tilPhone.error = state.message
                        binding.tilPhone.shakeAnimation(requireContext())
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
