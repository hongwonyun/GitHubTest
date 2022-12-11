package com.example.githubtest.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object bindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl", "placeHolder")
    fun ImageView.bindImage(url: String?, placeHolder: Drawable) {
        if (url.isNullOrEmpty()) {
            setImageDrawable(placeHolder)
        } else {
            Glide.with(context).load(url).apply {
                circleCrop()
                into(this@bindImage)
            }
        }
    }
}