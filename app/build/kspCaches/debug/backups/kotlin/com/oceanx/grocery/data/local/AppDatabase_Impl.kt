package com.oceanx.grocery.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _cartDao: Lazy<CartDao> = lazy {
    CartDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "175255fe85d1d184b8316b26bafe99da", "1936dcdef743deac1ff25aae40db62ec") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `cart_items` (`productId` INTEGER NOT NULL, `name` TEXT NOT NULL, `price` REAL NOT NULL, `imageRes` INTEGER NOT NULL, `unit` TEXT NOT NULL, `quantity` INTEGER NOT NULL, PRIMARY KEY(`productId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '175255fe85d1d184b8316b26bafe99da')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `cart_items`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsCartItems: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCartItems.put("productId", TableInfo.Column("productId", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("price", TableInfo.Column("price", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("imageRes", TableInfo.Column("imageRes", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("unit", TableInfo.Column("unit", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("quantity", TableInfo.Column("quantity", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCartItems: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCartItems: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCartItems: TableInfo = TableInfo("cart_items", _columnsCartItems,
            _foreignKeysCartItems, _indicesCartItems)
        val _existingCartItems: TableInfo = read(connection, "cart_items")
        if (!_infoCartItems.equals(_existingCartItems)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |cart_items(com.oceanx.grocery.data.local.CartItem).
              | Expected:
              |""".trimMargin() + _infoCartItems + """
              |
              | Found:
              |""".trimMargin() + _existingCartItems)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "cart_items")
  }

  public override fun clearAllTables() {
    super.performClear(false, "cart_items")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(CartDao::class, CartDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun cartDao(): CartDao = _cartDao.value
}
