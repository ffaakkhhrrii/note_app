package com.fakhri.notes.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import com.fakhri.notes.NotesViewModel
import com.fakhri.notes.NotesViewModelFactory
import com.fakhri.notes.R
import com.fakhri.notes.data.db.Notes
import com.fakhri.notes.screen.home.HomeScreen
import com.fakhri.notes.screen.notes.AddNotes

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    factory: NotesViewModelFactory,
    notesViewModel: NotesViewModel = viewModel(factory = factory)
) {
    NavHostApp(factory,notesViewModel)
}
