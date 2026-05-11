package com.oceanx.grocery.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.oceanx.grocery.R
import com.oceanx.grocery.databinding.FragmentCheckoutBinding
import kotlinx.coroutines.launch

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        binding.btnPlaceOrder.setOnClickListener {
            clearErrors()
            val name = binding.etName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val pincode = binding.etPincode.text.toString().trim()
            val paymentMode = when (binding.rgPayment.checkedRadioButtonId) {
                R.id.rbCod -> "COD"
                R.id.rbOnline -> "Online"
                else -> ""
            }
            viewModel.placeOrder(name, phone, address, pincode, paymentMode)
        }

        lifecycleScope.launch {
            viewModel.checkoutState.collect { state ->
                when (state) {
                    is CheckoutState.ValidationError -> {
                        when (state.field) {
                            "name" -> binding.tilName.error = state.message
                            "phone" -> binding.tilPhone.error = state.message
                            "address" -> binding.tilAddress.error = state.message
                            "pincode" -> binding.tilPincode.error = state.message
                            "payment" -> binding.tvPaymentError.apply {
                                text = state.message
                                visibility = View.VISIBLE
                            }
                        }
                    }
                    is CheckoutState.OrderPlaced -> {
                        findNavController().navigate(R.id.action_checkoutFragment_to_orderSuccessFragment)
                        viewModel.resetState()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun clearErrors() {
        binding.tilName.error = null
        binding.tilPhone.error = null
        binding.tilAddress.error = null
        binding.tilPincode.error = null
        binding.tvPaymentError.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
