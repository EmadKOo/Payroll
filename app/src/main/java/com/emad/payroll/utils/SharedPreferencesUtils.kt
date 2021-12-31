package com.emad.payroll.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesUtils @Inject constructor(@ApplicationContext private val context: Context) {
    fun saveToken(token: String = ""){
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences("Payroll", Context.MODE_PRIVATE).edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun readToken():String{
        val prefs: SharedPreferences = context.getSharedPreferences("Payroll",
            Context.MODE_PRIVATE
        )
        return prefs.getString("token", "").toString()
    }
}