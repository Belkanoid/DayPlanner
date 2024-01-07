package com.belkanoid.dayplanner.di

import android.app.Application
import com.belkanoid.dayplanner.presentation.screen.createEvent.CreateEventFragment
import com.belkanoid.dayplanner.presentation.screen.listEvent.EventPlannerFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface PlannerComponent {

    fun inject(eventPlannerFragment: EventPlannerFragment)
    fun inject(createEventFragment: CreateEventFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: Application,
        ): PlannerComponent
    }
}