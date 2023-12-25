package com.belkanoid.dayplanner.di

import android.app.Application
import com.belkanoid.dayplanner.data.localSource.PlannerDao
import com.belkanoid.dayplanner.data.localSource.PlannerDatabase
import com.belkanoid.dayplanner.data.repository.PlannerRepositoryImpl
import com.belkanoid.dayplanner.domain.PlannerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindPlannerRepository(impl: PlannerRepositoryImpl): PlannerRepository

    companion object {

        @Provides
        fun providePlannerDao(app: Application): PlannerDao = PlannerDatabase.getInstance(app).plannerDao()
    }

}