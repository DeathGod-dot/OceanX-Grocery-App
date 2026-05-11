package com.oceanx.grocery.`data`.local

import androidx.lifecycle.LiveData
import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class CartDao_Impl(
  __db: RoomDatabase,
) : CartDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfCartItem: EntityInsertAdapter<CartItem>

  private val __deleteAdapterOfCartItem: EntityDeleteOrUpdateAdapter<CartItem>
  init {
    this.__db = __db
    this.__insertAdapterOfCartItem = object : EntityInsertAdapter<CartItem>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `cart_items` (`productId`,`name`,`price`,`imageRes`,`unit`,`quantity`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CartItem) {
        statement.bindLong(1, entity.productId.toLong())
        statement.bindText(2, entity.name)
        statement.bindDouble(3, entity.price)
        statement.bindLong(4, entity.imageRes.toLong())
        statement.bindText(5, entity.unit)
        statement.bindLong(6, entity.quantity.toLong())
      }
    }
    this.__deleteAdapterOfCartItem = object : EntityDeleteOrUpdateAdapter<CartItem>() {
      protected override fun createQuery(): String =
          "DELETE FROM `cart_items` WHERE `productId` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CartItem) {
        statement.bindLong(1, entity.productId.toLong())
      }
    }
  }

  public override suspend fun insert(item: CartItem): Unit = performSuspending(__db, false, true) {
      _connection ->
    __insertAdapterOfCartItem.insert(_connection, item)
  }

  public override suspend fun delete(item: CartItem): Unit = performSuspending(__db, false, true) {
      _connection ->
    __deleteAdapterOfCartItem.handle(_connection, item)
  }

  public override fun getAllItems(): LiveData<List<CartItem>> {
    val _sql: String = "SELECT * FROM cart_items ORDER BY name ASC"
    return __db.invalidationTracker.createLiveData(arrayOf("cart_items"), false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "price")
        val _columnIndexOfImageRes: Int = getColumnIndexOrThrow(_stmt, "imageRes")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _result: MutableList<CartItem> = mutableListOf()
        while (_stmt.step()) {
          val _item: CartItem
          val _tmpProductId: Int
          _tmpProductId = _stmt.getLong(_columnIndexOfProductId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpPrice: Double
          _tmpPrice = _stmt.getDouble(_columnIndexOfPrice)
          val _tmpImageRes: Int
          _tmpImageRes = _stmt.getLong(_columnIndexOfImageRes).toInt()
          val _tmpUnit: String
          _tmpUnit = _stmt.getText(_columnIndexOfUnit)
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          _item = CartItem(_tmpProductId,_tmpName,_tmpPrice,_tmpImageRes,_tmpUnit,_tmpQuantity)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getItemById(productId: Int): CartItem? {
    val _sql: String = "SELECT * FROM cart_items WHERE productId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, productId.toLong())
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfPrice: Int = getColumnIndexOrThrow(_stmt, "price")
        val _columnIndexOfImageRes: Int = getColumnIndexOrThrow(_stmt, "imageRes")
        val _columnIndexOfUnit: Int = getColumnIndexOrThrow(_stmt, "unit")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _result: CartItem?
        if (_stmt.step()) {
          val _tmpProductId: Int
          _tmpProductId = _stmt.getLong(_columnIndexOfProductId).toInt()
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpPrice: Double
          _tmpPrice = _stmt.getDouble(_columnIndexOfPrice)
          val _tmpImageRes: Int
          _tmpImageRes = _stmt.getLong(_columnIndexOfImageRes).toInt()
          val _tmpUnit: String
          _tmpUnit = _stmt.getText(_columnIndexOfUnit)
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          _result = CartItem(_tmpProductId,_tmpName,_tmpPrice,_tmpImageRes,_tmpUnit,_tmpQuantity)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTotalItemCount(): LiveData<Int> {
    val _sql: String = "SELECT COALESCE(SUM(quantity), 0) FROM cart_items"
    return __db.invalidationTracker.createLiveData(arrayOf("cart_items"), false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int?
        if (_stmt.step()) {
          val _tmp: Int?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(0).toInt()
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getSubtotal(): LiveData<Double> {
    val _sql: String = "SELECT COALESCE(SUM(price * quantity), 0.0) FROM cart_items"
    return __db.invalidationTracker.createLiveData(arrayOf("cart_items"), false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Double?
        if (_stmt.step()) {
          val _tmp: Double?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getDouble(0)
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateQuantity(productId: Int, quantity: Int) {
    val _sql: String = "UPDATE cart_items SET quantity = ? WHERE productId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, quantity.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, productId.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearCart() {
    val _sql: String = "DELETE FROM cart_items"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
