package com.oceanx.grocery.data.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val category: String,
    val unit: String,
    val originalPrice: Double = price  // for showing strikethrough discount
)
