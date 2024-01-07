package com.belkanoid.dayplanner.data.repository

import com.belkanoid.dayplanner.data.localSource.dto.EventDto
import com.belkanoid.dayplanner.data.localSource.mapper.PlannerMapper.mapToEntityList
import com.belkanoid.dayplanner.domain.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream

object JsonParser {

    fun InputStream.parse(): List<Event> {
        val json = this.bufferedReader().use { it.readText() }

        val collectionType = object : TypeToken<List<EventDto>>() {}.type
        val events = Gson().fromJson<List<EventDto>>(json, collectionType)
        return events.mapToEntityList()
    }

}