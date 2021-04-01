package com.xj.anchortask

import android.content.Context
import android.content.SharedPreferences


fun getSP(name: String) = MyApplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE)!!

fun SharedPreferences.getSPEditor() = this.edit()!!

fun SharedPreferences.spApplyClear() = getSPEditor().clear().apply()

fun SharedPreferences.spCommitClear() = getSPEditor().clear().commit()

fun SharedPreferences.spApplyInt(key: String, value: Int) = getSPEditor().putInt(key, value).apply()

fun SharedPreferences.spCommitInt(key: String, value: Int) =
    getSPEditor().putInt(key, value).commit()

fun SharedPreferences.spGetInt(key: String, defValue: Int = 0): Int = this.getInt(key, defValue)

fun SharedPreferences.spApplyLong(key: String, value: Long) =
    getSPEditor().putLong(key, value).apply()

fun SharedPreferences.spCommitLong(key: String, value: Long) =
    getSPEditor().putLong(key, value).commit()

fun SharedPreferences.spGetLong(key: String, defValue: Long = 0): Long = this.getLong(key, defValue)

fun SharedPreferences.spApplyFloat(key: String, value: Float) =
    getSPEditor().putFloat(key, value).apply()

fun SharedPreferences.spCommitFloat(key: String, value: Float) =
    getSPEditor().putFloat(key, value).commit()

fun SharedPreferences.spGetFloat(key: String, defValue: Float = 0F): Float =
    this.getFloat(key, defValue)

fun SharedPreferences.spApplyBoolean(key: String, value: Boolean) =
    getSPEditor().putBoolean(key, value).apply()

fun SharedPreferences.spCommitBoolean(key: String, value: Boolean) =
    getSPEditor().putBoolean(key, value).commit()

fun SharedPreferences.spGetBoolean(key: String, defValue: Boolean = false): Boolean =
    this.getBoolean(key, defValue)

fun SharedPreferences.spApplyString(key: String, value: String) =
    getSPEditor().putString(key, value).apply()

fun SharedPreferences.spCommitString(key: String, value: String) =
    getSPEditor().putString(key, value).commit()

fun SharedPreferences.spGetString(key: String, defValue: String = ""): String? =
    this.getString(key, defValue)

fun SharedPreferences.spApplyStringSet(key: String, value: Set<String>) =
    getSPEditor().putStringSet(key, value).apply()

fun SharedPreferences.spCommitStringSet(key: String, value: Set<String>) =
    getSPEditor().putStringSet(key, value).commit()

fun SharedPreferences.spGetStringSet(key: String, defValue: Set<String>? = null): Set<String>? =
    this.getStringSet(key, defValue)

fun SharedPreferences.spCommitRemove(key: String) = getSPEditor().remove(key).commit()

fun SharedPreferences.spApplyRemove(key: String) = getSPEditor().remove(key).commit()