package com.example.fakestore.data.room.myCart.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class RoomMyCard (

    @PrimaryKey var id: Int,
    var category: String,
    var description: String,
    var image: String,
    var price: Double,
    var title: String,
    var count: Int
)