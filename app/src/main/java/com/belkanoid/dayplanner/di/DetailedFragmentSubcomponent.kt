package com.belkanoid.dayplanner.di

import com.belkanoid.dayplanner.domain.Event
import com.belkanoid.dayplanner.presentation.screens.detailedEvent.DetailedEventFragment
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(modules = [DomainModule::class])
interface DetailedFragmentSubcomponent {

    fun inject(detailedEventFragment: DetailedEventFragment)
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance event: Event
        ): DetailedFragmentSubcomponent
    }
}