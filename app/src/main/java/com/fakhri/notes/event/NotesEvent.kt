package com.fakhri.notes.event

sealed class NotesEvent {
    data class ValueEntered(val input: String):NotesEvent()
    object AddButtonClicked:NotesEvent()
}