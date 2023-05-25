package com.example.fakestore.data.room.favorites.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fakestore.data.room.favorites.entity.RoomFavorites

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: RoomFavorites)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg products: RoomFavorites)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<RoomFavorites>)

    @Update
    fun update(product: RoomFavorites)

    @Update
    fun update(vararg products: RoomFavorites)

    @Update
    fun update(products: List<RoomFavorites>)

//    @Query("UPDATE RoomFavorites SET count=:count WHERE id=:id")
//    fun updateCount(id: String, count: Int)

    @Delete
    fun delete(product: RoomFavorites)

    @Delete
    fun delete(vararg products: RoomFavorites)

    @Delete
    fun delete(products: List<RoomFavorites>)

    @Query("SELECT * FROM RoomFavorites")
    fun getAll(): List<RoomFavorites>

//    @Query("SELECT EXISTS (SELECT * FROM RoomMYCard WHERE id = :id)")
//    fun isContain(id: String): Boolean
}