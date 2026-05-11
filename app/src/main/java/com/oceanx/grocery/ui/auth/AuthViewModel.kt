package com.oceanx.grocery.ui.auth

import androidx.lifecycle.ViewModel
import com.oceanx.grocery.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthState {
    object Idle : AuthState()
    object PhoneValid : AuthState()
    data class PhoneError(val message: String) : AuthState()
    object OtpSent : AuthState()
    object OtpValid : AuthState()
    data class OtpError(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private var _phoneNumber: String = ""
    val phoneNumber get() = _phoneNumber

    fun validatePhone(phone: String) {
        _phoneNumber = phone
        when {
            phone.isBlank() -> _authState.value = AuthState.PhoneError("Phone number cannot be empty")
            phone.length != 10 -> _authState.value = AuthState.PhoneError("Enter a valid 10-digit mobile number")
            !phone.all { it.isDigit() } -> _authState.value = AuthState.PhoneError("Enter digits only")
            phone[0] !in '6'..'9' -> _authState.value = AuthState.PhoneError("Enter a valid Indian mobile number")
            else -> _authState.value = AuthState.OtpSent
        }
    }

    fun validateOtp(otp: String) {
        when {
            otp.length != 4 -> _authState.value = AuthState.OtpError("Enter complete 4-digit OTP")
            otp == Constants.FAKE_OTP -> _authState.value = AuthState.OtpValid
            else -> _authState.value = AuthState.OtpError("Incorrect OTP. Use ${Constants.FAKE_OTP}")
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
