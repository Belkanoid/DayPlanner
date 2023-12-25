package com.belkanoid.dayplanner.data.localSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.belkanoid.dayplanner.data.localSource.dto.EventDto
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannerDao {

    @Query("SELECT * from ${EventDto.TABLE_NAME} WHERE startTime BETWEEN :startDay AND :endDay")
    fun getEventFlowListForDay(startDay: Long, endDay: Long): Flow<List<EventDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventDto)

    @Query("DELETE from ${EventDto.TABLE_NAME} WHERE id=:eventId ")
    suspend fun deleteEvent(eventId: Int)

}