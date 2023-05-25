package com.example.fakestore.data.room.products.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fakestore.data.room.categories.entity.RoomCategories

@Entity(
    foreignKeys = [

        ForeignKey(
            entity = RoomCategories::class,
            parentColumns = ["category"],
            childColumns = ["category"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class RoomProducts (

    @PrimaryKey
    var id: Int,
    var category: String,
    var description: String,
    var image: String,
    var price: Double,
    var title: String,
    var favorite: Boolean
    )