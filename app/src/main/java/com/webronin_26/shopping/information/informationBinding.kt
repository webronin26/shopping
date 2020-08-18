package com.webronin_26.shopping.information

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:set_view_visibility")
fun setViewVisibility(view: View?, int: Int) {
    view?.let {
        view.visibility = int
    }
}

@BindingAdapter("app:set_text_string")
fun setTextString(textView: AppCompatTextView?, string: String?) {
    textView?.let {
        if (!string.isNullOrEmpty()) textView.text = string
    }
}

@BindingAdapter("app:set_text_int")
fun setTextInt(textView: AppCompatTextView?, int: Int?) {
    textView?.let {
        if (int != 0 && int != null) textView.text = int.toString()
    }
}