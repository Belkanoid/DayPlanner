package com.belkanoid.dayplanner.data.localSource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EventDto.TABLE_NAME)
data class EventDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val startTime: Long,
    val endTime: Long,
    val name: String,
    val description: String,
) {
    companion object {
        const val TABLE_NAME = "events_table"
    }
}
