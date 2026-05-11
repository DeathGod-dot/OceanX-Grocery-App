package com.oceanx.grocery.data

import com.oceanx.grocery.R
import com.oceanx.grocery.data.model.Product

object FakeProductData {

    val categories = listOf("All", "Fruits & Veggies", "Dairy", "Bakery & Snacks", "Personal Care")

    val products = listOf(
        // Fruits & Vegetables
        Product(1, "Fresh Banana", 29.0, R.drawable.ic_banana, "Fruits & Veggies", "6 pcs", 45.0),
        Product(2, "Red Tomatoes", 39.0, R.drawable.ic_tomato, "Fruits & Veggies", "500 g", 55.0),
        Product(3, "Baby Spinach", 49.0, R.drawable.ic_spinach, "Fruits & Veggies", "200 g", 65.0),
        Product(4, "Broccoli", 79.0, R.drawable.ic_broccoli, "Fruits & Veggies", "500 g", 99.0),
        Product(5, "Sweet Potato", 55.0, R.drawable.ic_potato, "Fruits & Veggies", "1 kg", 70.0),

        // Dairy & Beverages
        Product(6, "Full Cream Milk", 68.0, R.drawable.ic_milk, "Dairy", "1 L", 75.0),
        Product(7, "Greek Yogurt", 89.0, R.drawable.ic_yogurt, "Dairy", "400 g", 110.0),
        Product(8, "Butter Amul", 55.0, R.drawable.ic_butter, "Dairy", "100 g", 62.0),
        Product(9, "Orange Juice", 99.0, R.drawable.ic_juice, "Dairy", "1 L", 120.0),

        // Bakery & Snacks
        Product(10, "Whole Wheat Bread", 45.0, R.drawable.ic_bread, "Bakery & Snacks", "400 g", 50.0),
        Product(11, "Cream Crackers", 35.0, R.drawable.ic_crackers, "Bakery & Snacks", "200 g", 45.0),
        Product(12, "Dark Chocolate", 120.0, R.drawable.ic_chocolate, "Bakery & Snacks", "100 g", 150.0),

        // Personal Care
        Product(13, "Dove Soap", 49.0, R.drawable.ic_soap, "Personal Care", "3 pcs", 65.0),
        Product(14, "Head & Shoulders", 199.0, R.drawable.ic_shampoo, "Personal Care", "340 ml", 250.0),
        Product(15, "Colgate Toothpaste", 89.0, R.drawable.ic_toothpaste, "Personal Care", "150 g", 99.0)
    )
}
