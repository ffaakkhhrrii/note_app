package com.fakhri.notes.screen.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.fakhri.notes.data.db.Notes

@Composable
fun NotesList(
    modifier: Modifier = Modifier,
    noteList: State<List<Notes>>,
    onDeleteTask: (Notes) -> Unit,
    onDetailTask: (Notes,Int)-> Unit
) {
    LazyColumn(modifier = modifier) {
        items(noteList.value) {
            NotesItem(it, {notes->
                onDeleteTask(notes)
            },{notes,id->
                onDetailTask(notes,notes.id)
            })
        }
    }
}