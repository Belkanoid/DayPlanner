package com.belkanoid.dayplanner.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream

object JsonParser {

    fun <T> InputStream.parse(): T {
        val json = this.bufferedReader().use { it.readText() }

        val collectionType = object : TypeToken<T>() {}.type
        return Gson().fromJson(json, collectionType)
    }

}