package com.example.math

import android.content.Context

class SharedPreference(context: Context) {
    private val shared = context.getSharedPreferences("app_shared", 0)
    private val edit = shared.edit()

    companion object{
        private var instance : SharedPreference? = null
        fun getInstance(context: Context): SharedPreference {
            if (instance == null) instance = SharedPreference(context)
            return instance!!
        }
    }

    fun getRecord(level: Int): Int{
        return shared.getInt("record$level", 0)
    }

    fun setRecord(level: Int, score:Int){
        edit.putInt("record$level", score).apply()
    }
}