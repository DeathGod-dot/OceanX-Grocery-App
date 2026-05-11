package com.oceanx.grocery.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.grocery.data.model.Product
import com.oceanx.grocery.databinding.ItemProductBinding
import com.oceanx.grocery.utils.gone
import com.oceanx.grocery.utils.toCurrency
import com.oceanx.grocery.utils.visible

class ProductAdapter(
    private val onAddToCart: (Product) -> Unit,
    private val onIncrease: (Product) -> Unit,
    private val onDecrease: (Product) -> Unit,
    private val getCartQty: (Int, (Int) -> Unit) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallback()) {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                tvProductName.text = product.name
                tvProductUnit.text = product.unit
                tvProductPrice.text = product.price.toCurrency()
                ivProductImage.setImageResource(product.imageRes)

                if (product.originalPrice > product.price) {
                    tvOriginalPrice.visible()
                    tvOriginalPrice.text = product.originalPrice.toCurrency()
                    tvOriginalPrice.paintFlags =
                        tvOriginalPrice.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvOriginalPrice.gone()
                }

                // Refresh cart quantity
                refreshCartState(product)

                btnAdd.setOnClickListener {
                    onAddToCart(product)
                    refreshCartState(product)
                }
                btnIncrease.setOnClickListener {
                    onIncrease(product)
                    refreshCartState(product)
                }
                btnDecrease.setOnClickListener {
                    onDecrease(product)
                    refreshCartState(product)
                }
            }
        }

        private fun refreshCartState(product: Product) {
            getCartQty(product.id) { qty ->
                binding.apply {
                    if (qty > 0) {
                        btnAdd.gone()
                        layoutCounter.visible()
                        tvQuantity.text = qty.toString()
                    } else {
                        btnAdd.visible()
                        layoutCounter.gone()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}
