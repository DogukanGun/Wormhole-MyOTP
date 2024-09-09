package com.dag.check24di.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dag.check24di.data.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase: RoomDatabase() {
    abstract val dao: ProductDao
}