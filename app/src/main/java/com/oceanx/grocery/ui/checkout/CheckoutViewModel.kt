package com.oceanx.grocery.ui.checkout

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class CheckoutState {
    object Idle : CheckoutState()
    data class ValidationError(val field: String, val message: String) : CheckoutState()
    object OrderPlaced : CheckoutState()
}

class CheckoutViewModel : ViewModel() {

    private val _checkoutState = MutableStateFlow<CheckoutState>(CheckoutState.Idle)
    val checkoutState: StateFlow<CheckoutState> = _checkoutState.asStateFlow()

    fun placeOrder(
        name: String,
        phone: String,
        address: String,
        pincode: String,
        paymentMode: String
    ) {
        when {
            name.isBlank() ->
                _checkoutState.value = CheckoutState.ValidationError("name", "Full name is required")
            phone.isBlank() || phone.length != 10 || !phone.all { it.isDigit() } ->
                _checkoutState.value = CheckoutState.ValidationError("phone", "Enter valid 10-digit phone number")
            address.isBlank() ->
                _checkoutState.value = CheckoutState.ValidationError("address", "Delivery address is required")
            pincode.isBlank() || pincode.length != 6 || !pincode.all { it.isDigit() } ->
                _checkoutState.value = CheckoutState.ValidationError("pincode", "Enter valid 6-digit pincode")
            paymentMode.isBlank() ->
                _checkoutState.value = CheckoutState.ValidationError("payment", "Select a payment method")
            else ->
                _checkoutState.value = CheckoutState.OrderPlaced
        }
    }

    fun resetState() {
        _checkoutState.value = CheckoutState.Idle
    }
}
