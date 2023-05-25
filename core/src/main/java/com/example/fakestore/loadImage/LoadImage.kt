package com.example.fakestore.loadImage

import android.widget.ImageView
import com.bumptech.glide.Glide

class LoadImage: com.example.fakestore.image.ILoadImage<ImageView> {
    override fun loadImage(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }

    override fun loadImage(url: Int, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }

}