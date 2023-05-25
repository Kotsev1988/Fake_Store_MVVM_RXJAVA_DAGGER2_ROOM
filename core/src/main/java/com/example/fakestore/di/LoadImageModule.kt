package com.example.fakestore.di

import android.widget.ImageView
import com.example.fakestore.image.ILoadImage
import com.example.fakestore.loadImage.LoadImage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoadImageModule {

    @Singleton
    @Provides
    fun loadImage() : ILoadImage<ImageView> = LoadImage()
}