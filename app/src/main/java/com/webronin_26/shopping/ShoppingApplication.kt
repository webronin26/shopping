package com.webronin_26.shopping

import android.app.Application
import com.webronin_26.shopping.data.RepositoryProvider
import com.webronin_26.shopping.data.source.ShoppingRepository

class ShoppingApplication : Application() {

    val repository : ShoppingRepository
        get() = RepositoryProvider.provideShoppingRepository(this)
}