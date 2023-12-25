package com.belkanoid.dayplanner.presentation

import android.app.Application
import com.belkanoid.dayplanner.di.DaggerPlannerComponent
import com.belkanoid.dayplanner.di.PlannerComponent

class PlannerApplication: Application() {

    val component by lazy {
        DaggerPlannerComponent.factory().create(this)
    }
}