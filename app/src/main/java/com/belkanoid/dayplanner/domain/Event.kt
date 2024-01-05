package com.belkanoid.dayplanner.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: Int = UNDEFINED_ID,
    val startTime: Long,
    val endTime: Long,
    val name: String,
    val description: String,
) : Parcelable {
    companion object {
        const val UNDEFINED_ID = 0
    }
}

