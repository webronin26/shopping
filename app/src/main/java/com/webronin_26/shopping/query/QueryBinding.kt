package com.webronin_26.shopping.query

import android.graphics.Bitmap
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:picture")
fun setPicture(imageView: AppCompatImageView?, bitmap: Bitmap?) {
    if (imageView != null && bitmap != null) imageView.setImageBitmap(bitmap)
}

@BindingAdapter("app:set_text")
fun setText(textView: AppCompatTextView?, string: String?) {
    if (textView != null && string != null && string.isNotEmpty()) textView.text = string
}

@BindingAdapter("app:set_number")
fun setNumber(textView: AppCompatTextView?, int: Int?) {
    if (textView != null && int != null) textView.text = int.toString()
}