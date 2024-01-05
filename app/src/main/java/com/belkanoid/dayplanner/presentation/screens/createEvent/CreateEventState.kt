package com.belkanoid.dayplanner.presentation.screens.createEvent

sealed class CreateEventState {

    data object Empty: CreateEventState()
    data class Error(val message: String): CreateEventState()
    data class Success(val message: String): CreateEventState()

}