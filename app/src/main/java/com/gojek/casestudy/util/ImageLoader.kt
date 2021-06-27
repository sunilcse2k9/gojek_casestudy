package com.gojek.casestudy.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageLoader {

    fun loadImage(imageView: ImageView, imageUrl: String) {
        Picasso.get().load(imageUrl).into(imageView)
    }
}