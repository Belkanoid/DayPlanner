package com.belkanoid.dayplanner.di

import com.belkanoid.dayplanner.presentation.screens.detailedEvent.DetailedEventFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface DetailedFragmentSubcomponent {

    fun inject(detailedEventFragment: DetailedEventFragment)
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance eventId: Int
        ): DetailedFragmentSubcomponent
    }
}