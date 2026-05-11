package com.oceanx.grocery.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.oceanx.grocery.OceanXApp
import com.oceanx.grocery.data.local.CartItem
import com.oceanx.grocery.data.repository.CartRepository
import com.oceanx.grocery.utils.Constants
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartRepository: CartRepository

    val cartItems: LiveData<List<CartItem>>
    val subtotal: LiveData<Double>

    val deliveryFee = MediatorLiveData<Double>()
    val grandTotal = MediatorLiveData<Double>()

    init {
        val db = (application as OceanXApp).database
        cartRepository = CartRepository(db.cartDao())
        cartItems = cartRepository.allCartItems
        subtotal = cartRepository.subtotal

        deliveryFee.addSource(subtotal) { sub ->
            deliveryFee.value = if ((sub ?: 0.0) >= Constants.FREE_DELIVERY_THRESHOLD) 0.0
            else Constants.DELIVERY_FEE
        }

        grandTotal.addSource(subtotal) { sub ->
            val fee = deliveryFee.value ?: Constants.DELIVERY_FEE
            grandTotal.value = (sub ?: 0.0) + fee
        }

        grandTotal.addSource(deliveryFee) { fee ->
            val sub = subtotal.value ?: 0.0
            grandTotal.value = sub + fee
        }
    }

    fun increaseQuantity(item: CartItem) {
        viewModelScope.launch { cartRepository.increaseQuantity(item) }
    }

    fun decreaseQuantity(item: CartItem) {
        viewModelScope.launch { cartRepository.decreaseQuantity(item) }
    }

    fun removeItem(item: CartItem) {
        viewModelScope.launch { cartRepository.removeItem(item) }
    }

    fun clearCart() {
        viewModelScope.launch { cartRepository.clearCart() }
    }
}
