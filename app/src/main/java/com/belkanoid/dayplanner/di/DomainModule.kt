package com.belkanoid.dayplanner.di

import androidx.lifecycle.ViewModel
import com.belkanoid.dayplanner.presentation.screens.createEvent.CreateEventViewModel
import com.belkanoid.dayplanner.presentation.screens.detailedEvent.DetailedViewModel
import com.belkanoid.dayplanner.presentation.screens.listEvent.EventPlannerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DomainModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailedViewModel::class)
    fun bindDetailViewModel(impl: DetailedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventPlannerViewModel::class)
    fun bindEventPlannerViewModel(impl: EventPlannerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateEventViewModel::class)
    fun bindCreateEventViewModel(impl: CreateEventViewModel): ViewModel

}