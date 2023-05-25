package com.example.fakestore.data.room.favorites.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(

    //foreignKeys = [

//        ForeignKey(
//            entity = RoomProducts::class,
//            parentColumns = ["id"],
//            childColumns = ["idf"],
//            onDelete = ForeignKey.NO_ACTION
//        )]

)
data class RoomFavorites (

    @PrimaryKey
    var idf: Int,
    var isFavorite: Boolean
)