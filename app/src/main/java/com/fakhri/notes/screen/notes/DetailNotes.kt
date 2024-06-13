package com.fakhri.notes.screen.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fakhri.notes.NotesViewModel
import com.fakhri.notes.NotesViewModelFactory
import com.fakhri.notes.R
import com.fakhri.notes.data.db.Notes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailNotes(
    noteId: Int,
    factory: NotesViewModelFactory,
    notesViewModel: NotesViewModel = viewModel(factory = factory),
    modifier: Modifier = Modifier
) {
    val note by remember(noteId) { notesViewModel.note }.collectAsState()

    LaunchedEffect(noteId) {
        notesViewModel.fetchNoteById(noteId)
    }

    note?.let { notee ->
        LaunchedEffect(notee) {
            notesViewModel.inputTitle.value = notee.title
            notesViewModel.inputBody.value = notee.body
        }

        Column(modifier = modifier.padding(dimensionResource(R.dimen.medium))) {
            TextField(
                value = notesViewModel.inputTitle.value,
                onValueChange = { notesViewModel.inputTitle.value = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
                textStyle = MaterialTheme.typography.displayMedium,
                placeholder = {
                    Text(
                        text = "Masukkan judul",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            )

            TextField(
                value = notesViewModel.inputBody.value,
                onValueChange = { notesViewModel.inputBody.value = it },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                placeholder = {
                    Text(
                        text = "Masukkan isi",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    }
}
