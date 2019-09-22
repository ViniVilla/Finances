package br.edu.ifsp.finances.utils

import android.content.Context

fun getToken(context: Context?): String{
    val sharedPref = context?.getSharedPreferences("finances", Context.MODE_PRIVATE)
    return "Bearer " + sharedPref!!.getString("access_token", "")
}