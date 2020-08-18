package com.webronin_26.shopping.search

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:set_view_visibility")
fun setViewVisibility(view: View?, int: Int) {
    view?.let {
        view.visibility = int
    }
}

@BindingAdapter("app:name")
fun setName(textView: AppCompatTextView?, name: String?) {
    if (textView != null && name != null) textView.text = name
}

@BindingAdapter("app:price")
fun setPrice(textView: AppCompatTextView?, price: Int?) {
    if (textView != null && price != null) textView.text = price.toString()
}