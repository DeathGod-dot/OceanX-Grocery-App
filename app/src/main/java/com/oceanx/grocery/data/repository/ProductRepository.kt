package com.oceanx.grocery.data.repository

import com.oceanx.grocery.data.FakeProductData
import com.oceanx.grocery.data.model.Product

class ProductRepository {

    fun getAllProducts(): List<Product> = FakeProductData.products

    fun getCategories(): List<String> = FakeProductData.categories

    fun getProductsByCategory(category: String): List<Product> {
        return if (category == "All") FakeProductData.products
        else FakeProductData.products.filter { it.category == category }
    }

    fun searchProducts(query: String): List<Product> {
        if (query.isBlank()) return FakeProductData.products
        return FakeProductData.products.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }
    }
}
