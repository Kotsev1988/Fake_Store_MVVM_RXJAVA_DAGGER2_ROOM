package com.example.fakestore.image

interface ILoadImage<T> {
    fun loadImage(url:String, container: T)
    fun loadImage(url:Int, container: T)
}