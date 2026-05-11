package com.oceanx.grocery

import android.app.Application
import com.oceanx.grocery.data.local.AppDatabase

class OceanXApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
