package com.oceanx.grocery.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oceanx.grocery.databinding.FragmentOrderSuccessBinding
import com.oceanx.grocery.ui.cart.CartViewModel
import com.oceanx.grocery.ui.home.HomeActivity
import com.oceanx.grocery.utils.generateOrderId
import com.oceanx.grocery.utils.scaleIn
import com.oceanx.grocery.utils.Constants

class OrderSuccessFragment : Fragment() {

    private var _binding: FragmentOrderSuccessBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Clear the cart after order is placed
        cartViewModel.clearCart()

        // Set order details
        binding.tvOrderId.text = generateOrderId()
        binding.tvEstimatedTime.text = Constants.ESTIMATED_DELIVERY

        // Animate success card
        binding.cardSuccess.scaleIn()

        binding.btnContinueShopping.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
