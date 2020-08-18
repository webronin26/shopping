package com.webronin_26.shopping.data.source

import android.content.Context

object TokenManager {

    fun getToken(context: Context): String? =
        context.getSharedPreferences("token", Context.MODE_PRIVATE).getString("authorization","")

    fun setToken(context: Context, tokenString: String) =
        context.getSharedPreferences("token", Context.MODE_PRIVATE).edit().putString("authorization" , tokenString).apply()
}