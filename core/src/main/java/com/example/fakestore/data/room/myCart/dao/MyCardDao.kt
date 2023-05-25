package com.example.fakestore.data.room.myCart.dao

import androidx.room.*
import com.example.fakestore.data.room.myCart.entity.RoomMyCard

@Dao
interface MyCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: RoomMyCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg products: RoomMyCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<RoomMyCard>)

    @Update
    fun update(product: RoomMyCard)

    @Update
    fun update(vararg products: RoomMyCard)

    @Update
    fun update(products: List<RoomMyCard>)

    @Query("UPDATE RoomMYCard SET count=:count WHERE id=:id")
    fun updateCount(id: String, count: Int)

    @Delete
    fun delete(product: RoomMyCard)

    @Delete
    fun delete(vararg products: RoomMyCard)

    @Delete
    fun delete(products: List<RoomMyCard>)

    @Query("SELECT * FROM RoomMyCard")
    fun getAll(): List<RoomMyCard>

    @Query("SELECT EXISTS (SELECT * FROM RoomMYCard WHERE id = :id)")
    fun isContain(id: String): Boolean

}