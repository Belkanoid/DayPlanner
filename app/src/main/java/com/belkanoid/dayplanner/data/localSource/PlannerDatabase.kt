package com.belkanoid.dayplanner.data.localSource

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.belkanoid.dayplanner.data.localSource.dto.EventDto

@Database(entities = [EventDto::class], version = 1)
abstract class PlannerDatabase : RoomDatabase() {

    abstract fun plannerDao(): PlannerDao

    companion object {
        private var INSTANCE: PlannerDatabase? = null
        private const val DB_NAME = "day_planner.db"

        fun getInstance(app: Application): PlannerDatabase {
            INSTANCE?.let { return it }
            synchronized(this) {
                INSTANCE?.let { return it }
            }

            val db = Room.databaseBuilder(
                context = app,
                klass = PlannerDatabase::class.java,
                name = DB_NAME,
            ).build()

            INSTANCE = db
            return db

        }
    }
}