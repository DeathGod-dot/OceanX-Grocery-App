package com.oceanx.grocery.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.oceanx.grocery.databinding.FragmentOtpBinding
import com.oceanx.grocery.ui.home.HomeActivity
import com.oceanx.grocery.utils.shakeAnimation
import com.oceanx.grocery.utils.toast
import kotlinx.coroutines.launch

class OtpFragment : Fragment() {

    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()
    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSentTo.text = "OTP sent to +91 ${viewModel.phoneNumber}"

        setupOtpBoxes()
        startCountdown()

        binding.btnVerify.setOnClickListener {
            val otp = getEnteredOtp()
            viewModel.validateOtp(otp)
        }

        binding.tvResend.setOnClickListener {
            if (binding.tvResend.isEnabled) {
                clearOtpBoxes()
                startCountdown()
                toast("OTP resent!")
            }
        }

        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.OtpValid -> {
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    is AuthState.OtpError -> {
                        binding.layoutOtpBoxes.shakeAnimation(requireContext())
                        toast(state.message)
                        viewModel.resetState()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupOtpBoxes() {
        val boxes = listOf(
            binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4
        )

        boxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && index < boxes.size - 1) {
                        boxes[index + 1].requestFocus()
                    }
                    if (getEnteredOtp().length == 4) {
                        viewModel.validateOtp(getEnteredOtp())
                    }
                }
            })

            editText.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                    event.action == KeyEvent.ACTION_DOWN &&
                    editText.text.isNullOrEmpty() && index > 0
                ) {
                    boxes[index - 1].requestFocus()
                    boxes[index - 1].text?.clear()
                    true
                } else false
            }
        }
        boxes[0].requestFocus()
    }

    private fun getEnteredOtp(): String {
        return "${binding.etOtp1.text}${binding.etOtp2.text}${binding.etOtp3.text}${binding.etOtp4.text}"
    }

    private fun clearOtpBoxes() {
        binding.etOtp1.text?.clear()
        binding.etOtp2.text?.clear()
        binding.etOtp3.text?.clear()
        binding.etOtp4.text?.clear()
        binding.etOtp1.requestFocus()
    }

    private fun startCountdown() {
        binding.tvResend.isEnabled = false
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                binding.tvCountdown.text = "Resend OTP in ${seconds}s"
            }
            override fun onFinish() {
                binding.tvCountdown.text = ""
                binding.tvResend.isEnabled = true
            }
        }.start()
    }

    override fun onDestroyView() {
        countDownTimer?.cancel()
        super.onDestroyView()
        _binding = null
    }
}
