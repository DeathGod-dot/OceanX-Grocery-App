package com.oceanx.grocery.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.grocery.data.local.CartItem
import com.oceanx.grocery.databinding.ItemCartBinding
import com.oceanx.grocery.utils.toCurrency

class CartAdapter(
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit,
    private val onRemove: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(DiffCallback()) {

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.apply {
                tvCartItemName.text = item.name
                tvCartItemUnit.text = item.unit
                tvCartItemPrice.text = (item.price * item.quantity).toCurrency()
                tvCartItemQty.text = item.quantity.toString()
                ivCartItemImage.setImageResource(item.imageRes)

                btnCartIncrease.setOnClickListener { onIncrease(item) }
                btnCartDecrease.setOnClickListener { onDecrease(item) }
                btnRemoveItem.setOnClickListener { onRemove(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem.productId == newItem.productId
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem == newItem
    }
}
