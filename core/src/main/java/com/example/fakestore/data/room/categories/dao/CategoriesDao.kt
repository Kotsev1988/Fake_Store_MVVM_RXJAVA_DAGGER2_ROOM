package com.example.fakestore.data.room.categories.dao

import androidx.room.*
import com.example.fakestore.data.room.categories.entity.RoomCategories

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: RoomCategories)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg categories: RoomCategories)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: List<RoomCategories>)

    @Update
    fun update(category: RoomCategories)

    @Update
    fun update(vararg categories: RoomCategories)

    @Update
    fun update(categories: List<RoomCategories>)

    @Delete
    fun delete(category: RoomCategories)

    @Delete
    fun delete(vararg categories: RoomCategories)

    @Delete
    fun delete(categories: List<RoomCategories>)

    @Query("SELECT * FROM RoomCategories")
    fun getAll(): List<RoomCategories>


    @Query("SELECT * FROM RoomCategories WHERE category = :category LIMIT 1")
    fun findByCategoryId(category: String): RoomCategories

}