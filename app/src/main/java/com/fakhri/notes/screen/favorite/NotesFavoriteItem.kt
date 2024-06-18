package com.fakhri.notes.screen.favorite

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import com.fakhri.notes.R
import com.fakhri.notes.data.db.Notes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesFavoriteItem(
    notes: Notes,
    onDelete: (Notes) -> Unit,
    onDetail: (Notes, Int) -> Unit,
    onFavoriteClick: (Int,Boolean)->Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Hapus", style = MaterialTheme.typography.displayMedium)
            },
            text = {
                Text(
                    "Anda yakin ingin menghapus note ini?",
                    style = MaterialTheme.typography.labelSmall
                )
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", style = MaterialTheme.typography.bodyLarge)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(notes)
                    showDialog = false
                }) {
                    Text("OK", style = MaterialTheme.typography.bodyLarge)
                }
            }
        )
    }
    Card(
        modifier = modifier.padding(
            top = dimensionResource(R.dimen.small),
            start = dimensionResource(R.dimen.medium),
            end = dimensionResource(R.dimen.medium),
            bottom = dimensionResource(R.dimen.small)
        ).fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showDialog = true
                    },
                    onTap = {
                        onDetail(notes, notes.id)
                    }
                )
            },
    ) {
        Row(
            modifier = modifier.padding(dimensionResource(R.dimen.medium)).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = notes.title, style = MaterialTheme.typography.displayMedium)
            IconButton(onClick = { onFavoriteClick(notes.id,!notes.isFavorite) }) {
                Icon(
                    imageVector = if (notes.isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    }, contentDescription = null
                )
            }
        }
    }
}