package com.oceanx.grocery.data.repository

import androidx.lifecycle.LiveData
import com.oceanx.grocery.data.local.CartDao
import com.oceanx.grocery.data.local.CartItem

class CartRepository(private val cartDao: CartDao) {

    val allCartItems: LiveData<List<CartItem>> = cartDao.getAllItems()
    val totalItemCount: LiveData<Int> = cartDao.getTotalItemCount()
    val subtotal: LiveData<Double> = cartDao.getSubtotal()

    suspend fun addToCart(item: CartItem) {
        val existing = cartDao.getItemById(item.productId)
        if (existing != null) {
            cartDao.updateQuantity(item.productId, existing.quantity + 1)
        } else {
            cartDao.insert(item)
        }
    }

    suspend fun increaseQuantity(item: CartItem) {
        cartDao.updateQuantity(item.productId, item.quantity + 1)
    }

    suspend fun decreaseQuantity(item: CartItem) {
        if (item.quantity <= 1) {
            cartDao.delete(item)
        } else {
            cartDao.updateQuantity(item.productId, item.quantity - 1)
        }
    }

    suspend fun removeItem(item: CartItem) {
        cartDao.delete(item)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun getItemById(productId: Int): CartItem? {
        return cartDao.getItemById(productId)
    }
}
