package com.belkanoid.dayplanner.presentation.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.belkanoid.dayplanner.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge


fun ViewBinding.showSnackbar(message: String, color: Int = R.color.black) {
    val snackbar = Snackbar.make(this.root, message, Snackbar.LENGTH_SHORT)
    snackbar.setBackgroundTint(ContextCompat.getColor(this.root.context, color))
    snackbar.show()

}

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> = merge(this, another)
