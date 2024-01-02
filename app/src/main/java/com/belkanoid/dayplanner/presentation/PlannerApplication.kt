package com.belkanoid.dayplanner.presentation

import android.app.Application
import com.belkanoid.dayplanner.di.DaggerPlannerComponent

class PlannerApplication: Application() {

    val component by lazy {
        DaggerPlannerComponent.factory().create(this)
    }
}