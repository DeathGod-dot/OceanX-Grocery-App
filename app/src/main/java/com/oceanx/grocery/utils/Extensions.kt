package com.oceanx.grocery.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.oceanx.grocery.R

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    requireContext().toast(message)
}

fun View.visible() { visibility = View.VISIBLE }
fun View.gone() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun View.shakeAnimation(context: Context) {
    val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
    startAnimation(shake)
}

fun View.fadeIn() {
    alpha = 0f
    visible()
    animate().alpha(1f).setDuration(300).start()
}

fun View.scaleIn() {
    scaleX = 0.5f
    scaleY = 0.5f
    alpha = 0f
    visible()
    animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(400).start()
}

fun Double.toCurrency(): String = "₹%.2f".format(this)

fun generateOrderId(): String {
    val chars = "0123456789"
    return "#OX-" + (1..6).map { chars.random() }.joinToString("")
}
