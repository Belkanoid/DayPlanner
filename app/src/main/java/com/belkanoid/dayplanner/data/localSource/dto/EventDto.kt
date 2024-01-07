package com.belkanoid.dayplanner.data.localSource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = EventDto.TABLE_NAME)
data class EventDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("date_start")
    val startTime: Long,
    @SerializedName("date_finish")
    val endTime: Long,
    val name: String,
    val description: String,
) {
    companion object {
        const val TABLE_NAME = "events_table"
    }
}
