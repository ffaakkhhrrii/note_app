package com.fakhri.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhri.notes.data.NotesRepository
import com.fakhri.notes.data.db.Notes
import com.fakhri.notes.event.NotesEvent
import com.fakhri.notes.event.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    val inputTitle: MutableState<String> = mutableStateOf("")
    val inputBody: MutableState<String> = mutableStateOf("")
    val isFavorite = mutableStateOf(false)

    val noteList = repository.getAllNotes()

    fun addNotes(title: String, body: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNotes(Notes(0, title, body, isFavorite))
            inputTitle.value = ""
            inputBody.value = ""
        }
    }

    fun deleteNotes(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotes(notes)
        }
    }

    fun updateNotes(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotes(notes)
            inputTitle.value = ""
            inputBody.value = ""
        }
    }

    private val _note = MutableStateFlow<Notes?>(null)
    val note: StateFlow<Notes?> get() = _note

    fun fetchNoteById(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _note.value = repository.getNotesById(noteId)
        }
    }


    fun navBack() {
        inputTitle.value = ""
        inputBody.value = ""
    }

    val notesFavoriteList = repository.getFavoriteNotes(true)

    fun updateFavoriteStatus(noteId: Int,isFavorite: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(noteId,isFavorite)
        }
    }
}