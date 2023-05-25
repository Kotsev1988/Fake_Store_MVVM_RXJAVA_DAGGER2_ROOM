package com.example.fakestore.data.room.products.dao

import androidx.room.*
import com.example.fakestore.data.room.products.entity.RoomProducts

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: RoomProducts)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (vararg products: RoomProducts)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomProducts>)

    @Update
    fun update(products: RoomProducts)

    @Update
    fun update(vararg products: RoomProducts)

    @Update
    fun update(product: List<RoomProducts>)

    @Delete
    fun delete(products: RoomProducts)

    @Delete
    fun delete(vararg products: RoomProducts)

    @Delete
    fun delete(user: List<RoomProducts>)

    @Query("SELECT * FROM RoomProducts")
    fun getAll(): List<RoomProducts>

    @Query("SELECT * FROM RoomProducts WHERE id = :id")
    fun getProductById(id: String): RoomProducts

    @Query("SELECT * FROM RoomProducts WHERE category = :category")
    fun findCategoryById(category: String): List<RoomProducts>
}