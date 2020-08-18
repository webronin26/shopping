package com.webronin_26.shopping.main

import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:picture")
fun setPicture(imageView: AppCompatImageView?, bitmap: Bitmap?) {
    if (imageView != null && bitmap != null) imageView.setImageBitmap(bitmap)
}

@BindingAdapter("app:product_text")
fun setText(textView: AppCompatTextView?, text: String?) {
    if (textView != null && text != null) textView.text = text
}
