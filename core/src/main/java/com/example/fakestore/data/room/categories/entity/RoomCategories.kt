package com.example.fakestore.data.room.categories.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomCategories (

    @PrimaryKey
    var category: String,
    var select: Boolean

    )