package com.fakhri.notes.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fakhri.notes.data.db.Notes
import com.fakhri.notes.screen.home.NotesList

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    noteList: State<List<Notes>>,
    onDeleteTask: (Notes) -> Unit,
    onDetailTask: (Notes, Int) -> Unit,
    onFavoriteTask: (Int, Boolean) -> Unit
) {
    if ((noteList.value).isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tidak ada notes favorite", style = MaterialTheme.typography.displayMedium)
        }
    }
    NotesFavoriteList(
        noteList = noteList,
        onDeleteTask = { item -> onDeleteTask(item) },
        onDetailTask = { notes, i -> onDetailTask(notes, notes.id) },
        onFavoriteTask = { id, isFavorite -> onFavoriteTask(id, isFavorite) })
}