package com.belkanoid.dayplanner.di

import android.app.Application
import com.belkanoid.dayplanner.presentation.screens.createEvent.CreateEventFragment
import com.belkanoid.dayplanner.presentation.screens.eventPlanner.EventPlannerFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface PlannerComponent {

    fun detailedFragmentComponentFactory(): DetailedFragmentSubcomponent.Factory
    fun inject(eventPlannerFragment: EventPlannerFragment)
    fun inject(createEventFragment: CreateEventFragment)


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: Application,
        ): PlannerComponent
    }
}