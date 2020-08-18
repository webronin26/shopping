package com.webronin_26.shopping.data.source

import android.content.Context
import android.content.SharedPreferences

object UserInformationManager {

    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun saveUserName(context: Context, Name: String) =
        getSharedPreferences(context).edit().putString("name" , Name).apply()

    fun getUserName(context: Context): String? =
        getSharedPreferences(context).getString("name","")

    fun saveUserID(context: Context, id: Int) =
        getSharedPreferences(context).edit().putInt("id" , id).apply()

    fun getUserID(context: Context): Int? =
        getSharedPreferences(context).getInt("id",0)
}
