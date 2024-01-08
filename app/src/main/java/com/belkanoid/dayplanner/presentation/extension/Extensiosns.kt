package com.belkanoid.dayplanner.presentation.extension

import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.belkanoid.dayplanner.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge


fun ViewBinding.showErrorSnackbar(message: String) {
    showSnackbar(message, R.color.error)
}

fun ViewBinding.showSuccessSnackbar(message: String) {
    showSnackbar(message, R.color.success)
}

fun ViewBinding.showSnackbar(message: String, color: Int = R.color.black) {
    val snackbar = Snackbar.make(this.root, message, Snackbar.LENGTH_SHORT)
    snackbar.setBackgroundTint(ContextCompat.getColor(this.root.context, color))
    snackbar.show()
}

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> = merge(this, another)
