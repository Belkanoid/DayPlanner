package com.belkanoid.dayplanner.data.repository

import android.app.Application
import androidx.annotation.StringRes
import javax.inject.Inject


class ResourcesProvider @Inject constructor(
    private val app: Application
) {
    fun getString(@StringRes stringResId: Int): String {
        return app.getString(stringResId)
    }
}