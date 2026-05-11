package com.oceanx.grocery.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oceanx.grocery.OceanXApp
import com.oceanx.grocery.data.local.CartItem
import com.oceanx.grocery.data.model.Product
import com.oceanx.grocery.data.repository.CartRepository
import com.oceanx.grocery.data.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepository()
    private val cartRepository: CartRepository

    val cartItemCount: LiveData<Int>

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _selectedCategory = MutableLiveData("All")
    val selectedCategory: LiveData<String> = _selectedCategory

    private val _searchQuery = MutableLiveData("")

    init {
        val db = (application as OceanXApp).database
        cartRepository = CartRepository(db.cartDao())
        cartItemCount = cartRepository.totalItemCount
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = productRepository.getAllProducts()
    }

    fun filterByCategory(category: String) {
        _selectedCategory.value = category
        val query = _searchQuery.value ?: ""
        applyFilter(category, query)
    }

    fun search(query: String) {
        _searchQuery.value = query
        val category = _selectedCategory.value ?: "All"
        applyFilter(category, query)
    }

    private fun applyFilter(category: String, query: String) {
        var list = productRepository.getProductsByCategory(category)
        if (query.isNotBlank()) {
            list = list.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        _products.value = list
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val cartItem = CartItem(
                productId = product.id,
                name = product.name,
                price = product.price,
                imageRes = product.imageRes,
                unit = product.unit,
                quantity = 1
            )
            cartRepository.addToCart(cartItem)
        }
    }

    fun increaseCartItem(product: Product) {
        viewModelScope.launch {
            val existing = cartRepository.getItemById(product.id)
            existing?.let { cartRepository.increaseQuantity(it) }
        }
    }

    fun decreaseCartItem(product: Product) {
        viewModelScope.launch {
            val existing = cartRepository.getItemById(product.id)
            existing?.let { cartRepository.decreaseQuantity(it) }
        }
    }

    fun getCartItem(productId: Int, callback: (CartItem?) -> Unit) {
        viewModelScope.launch {
            callback(cartRepository.getItemById(productId))
        }
    }

    fun getCategories() = productRepository.getCategories()
}
