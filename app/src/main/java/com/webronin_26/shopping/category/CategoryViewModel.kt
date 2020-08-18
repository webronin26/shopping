package com.webronin_26.shopping.category

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.webronin_26.shopping.data.source.ShoppingRepository

class CategoryViewModel : ViewModel(), LifecycleObserver {

    var repository: ShoppingRepository? = null
}