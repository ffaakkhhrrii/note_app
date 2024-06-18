package com.fakhri.notes.screen.favorite

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.fakhri.notes.data.db.Notes
import com.fakhri.notes.screen.home.NotesItem

@Composable
fun NotesFavoriteList(
    modifier: Modifier = Modifier,
    noteList: State<List<Notes>>,
    onDeleteTask: (Notes) -> Unit,
    onDetailTask: (Notes, Int) -> Unit,
    onFavoriteTask: (Int, Boolean) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(noteList.value) {
            NotesFavoriteItem(it, { notes ->
                onDeleteTask(notes)
            }, { notes, id ->
                onDetailTask(notes, notes.id)
            }, { id, isFavorite ->
                onFavoriteTask(id, isFavorite)
            })
        }
    }
}