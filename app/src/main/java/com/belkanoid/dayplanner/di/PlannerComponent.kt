package com.belkanoid.dayplanner.di

import android.app.Application
import com.belkanoid.dayplanner.presentation.screens.eventPlanner.EventPlannerFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Factory

@Component(modules = [DataModule::class])
interface PlannerComponent {

    fun inject(eventPlannerFragment: EventPlannerFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: Application,
        ): PlannerComponent
    }
}