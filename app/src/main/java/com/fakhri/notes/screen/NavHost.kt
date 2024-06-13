package com.fakhri.notes.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fakhri.notes.NotesViewModel
import com.fakhri.notes.NotesViewModelFactory
import com.fakhri.notes.R
import com.fakhri.notes.data.db.Notes
import com.fakhri.notes.screen.home.HomeScreen
import com.fakhri.notes.screen.notes.AddNotes
import com.fakhri.notes.screen.notes.DetailNotes

enum class NotesScreen(val title: String) {
    Home("Home"),
    AddNotes("Notes"),
    DetailNotes("Detail")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavHostApp(
    factory: NotesViewModelFactory,
    notesViewModel: NotesViewModel = viewModel(factory = factory),
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route ?: NotesScreen.Home.name
    val currentScreen = when (route) {
        NotesScreen.Home.name -> NotesScreen.Home
        NotesScreen.AddNotes.name -> NotesScreen.AddNotes
        else -> NotesScreen.DetailNotes
    }
    val canBack = navController.previousBackStackEntry != null
    var isUpdate by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        NotesAppBar(
            factory,
            canNavigateBack = canBack,
            navigateAdd = { navController.navigate(NotesScreen.AddNotes.name) },
            navigateBack = {
                navController.navigateUp()
                isUpdate = !isUpdate
            },
            notesViewModel = notesViewModel,
            currentScreen = currentScreen,
            addNotes = { title, body, isfav ->
                notesViewModel.addNotes(title, body, isfav)
                navController.navigateUp()
            },
            isUpdate = isUpdate,
            updateNotes = { note ->
                notesViewModel.updateNotes(
                    note
                )
                navController.navigateUp()
                isUpdate = !isUpdate
            },
        )
    }) { it ->
        NavHost(
            navController = navController,
            startDestination = NotesScreen.Home.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = NotesScreen.Home.name) {
                val noteList = notesViewModel.noteList.collectAsState(initial = emptyList())
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(noteList = noteList, onDeleteTask = {
                        notesViewModel.deleteNotes(it)
                    }, onDetailTask = { notes, id ->
                        navController.navigate("${NotesScreen.DetailNotes.name}/${id}")
                        isUpdate = !isUpdate
                    })
                }
            }
            composable(route = NotesScreen.AddNotes.name) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AddNotes(notesViewModel.inputTitle, notesViewModel.inputBody)
                }
            }
            composable(
                route = "${NotesScreen.DetailNotes.name}/{noteId}",
                arguments = listOf(navArgument("noteId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                Surface(modifier = Modifier.fillMaxSize()) {
                    backStackEntry.arguments?.getInt("noteId")
                        ?.let { it1 ->
                            DetailNotes(
                                noteId = it1,
                                factory = factory,
                                notesViewModel = notesViewModel
                            )
                        }
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAppBar(
    factory: NotesViewModelFactory,
    canNavigateBack: Boolean,
    isUpdate: Boolean,
    currentScreen: NotesScreen,
    notesViewModel: NotesViewModel = viewModel(factory = factory),
    addNotes: (String, String, Boolean) -> Unit,
    navigateAdd: () -> Unit,
    navigateBack: () -> Unit,
    updateNotes: (Notes) -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentScreen.title,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        actions = {
            if (canNavigateBack && !isUpdate) {
                IconButton(
                    onClick = {
                        if (notesViewModel.inputTitle.value.isNotEmpty() && notesViewModel.inputBody.value.isNotEmpty()) {
                            addNotes(
                                notesViewModel.inputTitle.value,
                                notesViewModel.inputBody.value,
                                false
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null)
                }
            } else if (isUpdate && canNavigateBack) {
                IconButton(
                    onClick = {
                        if (notesViewModel.inputTitle.value.isNotEmpty() && notesViewModel.inputBody.value.isNotEmpty()) {
                            updateNotes(
                                Notes(
                                    notesViewModel.note.value!!.id,
                                    notesViewModel.inputTitle.value,
                                    notesViewModel.inputBody.value,
                                    false
                                )
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null)
                }
            } else {
                IconButton(
                    onClick = navigateAdd,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}