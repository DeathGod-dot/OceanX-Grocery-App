package com.oceanx.grocery.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oceanx.grocery.R
import com.oceanx.grocery.databinding.FragmentCartBinding
import com.oceanx.grocery.utils.gone
import com.oceanx.grocery.utils.toCurrency
import com.oceanx.grocery.utils.visible

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.btnProceedCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }

        binding.btnShopNow.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onIncrease = { viewModel.increaseQuantity(it) },
            onDecrease = { viewModel.decreaseQuantity(it) },
            onRemove = { viewModel.removeItem(it) }
        )
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items)
            if (items.isEmpty()) {
                binding.layoutEmpty.visible()
                binding.layoutCartContent.gone()
            } else {
                binding.layoutEmpty.gone()
                binding.layoutCartContent.visible()
            }
        }

        viewModel.subtotal.observe(viewLifecycleOwner) { sub ->
            binding.tvSubtotal.text = (sub ?: 0.0).toCurrency()
        }

        viewModel.deliveryFee.observe(viewLifecycleOwner) { fee ->
            if ((fee ?: 0.0) == 0.0) {
                binding.tvDeliveryFee.text = "FREE"
                binding.tvDeliveryFee.setTextColor(
                    resources.getColor(R.color.accent_green, null)
                )
            } else {
                binding.tvDeliveryFee.text = (fee ?: 0.0).toCurrency()
                binding.tvDeliveryFee.setTextColor(
                    resources.getColor(R.color.on_surface, null)
                )
            }
        }

        viewModel.grandTotal.observe(viewLifecycleOwner) { total ->
            binding.tvGrandTotal.text = (total ?: 0.0).toCurrency()
            binding.btnProceedCheckout.text = "Proceed to Pay ${(total ?: 0.0).toCurrency()}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
