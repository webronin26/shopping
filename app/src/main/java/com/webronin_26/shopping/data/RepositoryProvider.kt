package com.webronin_26.shopping.data

import android.content.Context
import com.webronin_26.shopping.data.source.ShoppingRepository

object RepositoryProvider {

    @Volatile
    var shoppingRepository : ShoppingRepository? = null

    fun provideShoppingRepository(context: Context): ShoppingRepository {
        synchronized(this) {
            return  shoppingRepository ?: shoppingRepository ?: createShoppingRepository(context)
        }
    }

    private fun createShoppingRepository(context: Context): ShoppingRepository = ShoppingRepository(context)
}
