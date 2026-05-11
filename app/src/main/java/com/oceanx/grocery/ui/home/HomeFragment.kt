package com.oceanx.grocery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.oceanx.grocery.R
import com.oceanx.grocery.data.local.CartItem
import com.oceanx.grocery.data.model.Product
import com.oceanx.grocery.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupCategoryChips()
        setupSearch()
        observeViewModel()

        binding.fabCart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            onAddToCart = { product -> viewModel.addToCart(product) },
            onIncrease = { product -> viewModel.increaseCartItem(product) },
            onDecrease = { product -> viewModel.decreaseCartItem(product) },
            getCartQty = { productId, callback ->
                viewModel.getCartItem(productId) { item -> callback(item?.quantity ?: 0) }
            }
        )
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupCategoryChips() {
        val categories = viewModel.getCategories()
        categories.forEach { category ->
            val chip = Chip(requireContext()).apply {
                text = category
                isCheckable = true
                isChecked = category == "All"
                setChipBackgroundColorResource(R.color.chip_selector)
                setTextColor(resources.getColorStateList(R.color.chip_text_selector, null))
                chipCornerRadius = 24f
            }
            chip.setOnClickListener {
                viewModel.filterByCategory(category)
                updateChipSelection(category)
            }
            binding.chipGroupCategories.addView(chip)
        }
    }

    private fun updateChipSelection(selectedCategory: String) {
        for (i in 0 until binding.chipGroupCategories.childCount) {
            val chip = binding.chipGroupCategories.getChildAt(i) as? Chip
            chip?.isChecked = chip?.text == selectedCategory
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query ?: "")
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText ?: "")
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.submitList(products)
            if (products.isEmpty()) {
                binding.tvNoProducts.visibility = View.VISIBLE
                binding.rvProducts.visibility = View.GONE
            } else {
                binding.tvNoProducts.visibility = View.GONE
                binding.rvProducts.visibility = View.VISIBLE
            }
        }

        viewModel.cartItemCount.observe(viewLifecycleOwner) { count ->
            if (count > 0) {
                binding.tvCartBadge.visibility = View.VISIBLE
                binding.tvCartBadge.text = count.toString()
            } else {
                binding.tvCartBadge.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
