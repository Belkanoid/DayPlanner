package com.belkanoid.dayplanner.di

import android.app.Application
import com.belkanoid.dayplanner.data.localSource.PlannerDao
import com.belkanoid.dayplanner.data.localSource.PlannerDatabase
import com.belkanoid.dayplanner.data.repository.DateConverter
import com.belkanoid.dayplanner.data.repository.PlannerRepositoryImpl
import com.belkanoid.dayplanner.domain.PlannerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    fun bindPlannerRepository(impl: PlannerRepositoryImpl): PlannerRepository

    @Binds
    fun bindDateConverter(impl: DateConverter): DateConverter

    companion object {

        @Singleton
        @Provides
        fun providePlannerDao(app: Application): PlannerDao = PlannerDatabase.getInstance(app).plannerDao()
    }

}