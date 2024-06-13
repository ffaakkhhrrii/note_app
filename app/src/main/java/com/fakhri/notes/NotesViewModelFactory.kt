package com.fakhri.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fakhri.notes.data.NotesRepository
import javax.inject.Inject

class NotesViewModelFactory @Inject constructor(private val repository: NotesRepository) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(repository) as T
    }
}