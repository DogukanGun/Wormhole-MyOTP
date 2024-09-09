package com.dag.check24di.dao

import androidx.room.Dao
import androidx.room.Query
import com.dag.check24di.data.Product
import androidx.room.Upsert
import com.dag.check24di.data.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Upsert
    suspend fun upsertProduct(product: ProductEntity)

    @Query("SELECT * FROM productentity where isDeleted == 0")
    fun getAllRecords(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM productentity where id == :id")
    fun getProduct(id:Long): Flow<ProductEntity?>

}