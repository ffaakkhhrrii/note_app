package com.fakhri.notes.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fakhri.notes.data.db.Notes

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    noteList: State<List<Notes>>,
    onDeleteTask: (Notes) -> Unit,
    onDetailTask:(Notes,Int)-> Unit
) {
    if ((noteList.value).isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tidak ada notes", style = MaterialTheme.typography.displayMedium)
        }
    }
    NotesList(noteList = noteList, onDeleteTask = {item -> onDeleteTask(item)}, onDetailTask = {notes,i-> onDetailTask(notes,notes.id)})
}