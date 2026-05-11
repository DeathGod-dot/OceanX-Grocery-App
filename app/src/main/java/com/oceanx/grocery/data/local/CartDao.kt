package com.oceanx.grocery.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items ORDER BY name ASC")
    fun getAllItems(): LiveData<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getItemById(productId: Int): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)

    @Delete
    suspend fun delete(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart_items")
    fun getTotalItemCount(): LiveData<Int>

    @Query("SELECT COALESCE(SUM(price * quantity), 0.0) FROM cart_items")
    fun getSubtotal(): LiveData<Double>
}
