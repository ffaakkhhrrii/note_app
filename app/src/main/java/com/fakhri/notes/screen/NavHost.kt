package com.fakhri.notes.screen

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import com.fakhri.notes.screen.favorite.FavoriteScreen
import com.fakhri.notes.screen.home.HomeScreen
import com.fakhri.notes.screen.notes.AddNotes
import com.fakhri.notes.screen.notes.DetailNotes

enum class NotesScreen(val title: String) {
    Home("Home"),
    AddNotes("Notes"),
    DetailNotes("Detail"),
    Favorite("Favorite")
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
        NotesScreen.Favorite.name -> NotesScreen.Favorite
        else -> NotesScreen.DetailNotes
    }

    val canBack = navController.previousBackStackEntry != null
    val bottomBarHeight = 56.dp

    Scaffold(bottomBar = {
        if (currentScreen != NotesScreen.AddNotes && currentScreen != NotesScreen.DetailNotes) {
            BottomBar(navController = navController)
        }
    }) {
        NavHost(
            navController = navController,
            startDestination = NotesScreen.Home.name,
        ) {
            composable(route = NotesScreen.Home.name) {
                val noteList = notesViewModel.noteList.collectAsState(initial = emptyList())
                Scaffold(topBar = {
                    HomeBar(canBack) {
                        navController.navigate(NotesScreen.AddNotes.name)
                    }
                }) {
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(it)
                            .padding(bottom = bottomBarHeight)
                    ) {
                        HomeScreen(noteList = noteList, onDeleteTask = {
                            notesViewModel.deleteNotes(it)
                        }, onDetailTask = { notes, id ->
                            navController.navigate("${NotesScreen.DetailNotes.name}/${id}")
                        }, onFavoriteTask = { id, isFavorite ->
                            notesViewModel.updateFavoriteStatus(id, isFavorite)
                        })
                    }
                }
            }
            composable(route = NotesScreen.AddNotes.name) {
                Scaffold(topBar = {
                    AddTopBar(factory, canBack, notesViewModel, { title, body, isfav ->
                        notesViewModel.addNotes(title, body, isfav)
                        navController.navigateUp()
                    }, navigateBack = {
                        navController.navigateUp()
                        notesViewModel.navBack()
                    })
                }) {
                    Surface(modifier = Modifier.fillMaxSize().padding(it)) {
                        AddNotes(notesViewModel.inputTitle, notesViewModel.inputBody)
                    }
                }
            }
            composable(
                route = "${NotesScreen.DetailNotes.name}/{noteId}",
                arguments = listOf(navArgument("noteId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                Scaffold(topBar = {
                    DetailTopBar(factory, canBack, notesViewModel, { note ->
                        notesViewModel.updateNotes(
                            note
                        )
                        navController.navigateUp()
                    }, navigateBack = {
                        navController.navigateUp()
                        notesViewModel.navBack()
                    })
                }) {
                    Surface(modifier = Modifier.fillMaxSize().padding(it)) {
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
            composable(route = NotesScreen.Favorite.name) {
                Scaffold(topBar = {
                    FavoriteBar(canBack) {
                        navController.navigate(NotesScreen.AddNotes.name)
                    }
                }) {
                    Surface(modifier = Modifier.fillMaxSize().padding(it)) {
                        FavoriteScreen(
                            noteList = notesViewModel.notesFavoriteList.collectAsState(initial = emptyList()),
                            onDeleteTask = {
                                notesViewModel.deleteNotes(it)
                            },
                            onDetailTask = { note, id ->
                                navController.navigate("${NotesScreen.DetailNotes.name}/${id}")
                            }, onFavoriteTask = { id, isFavorite ->
                                notesViewModel.updateFavoriteStatus(id, isFavorite)
                            })
                    }
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBar(
    canNavigateBack: Boolean,
    navigateAdd: () -> Unit
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
                    text = "Home",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        actions = {
            IconButton(
                onClick = navigateAdd,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }

        },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBar(
    canNavigateBack: Boolean,
    navigateAdd: () -> Unit
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
                    text = "Favorite",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        actions = {
            IconButton(
                onClick = navigateAdd,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }

        },
        modifier = Modifier.fillMaxWidth()
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTopBar(
    factory: NotesViewModelFactory,
    canNavigateBack: Boolean,
    notesViewModel: NotesViewModel = viewModel(factory = factory),
    addNotes: (String, String, Boolean) -> Unit,
    navigateBack: () -> Unit,
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
                    text = "Notes",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        actions = {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    factory: NotesViewModelFactory,
    canNavigateBack: Boolean,
    notesViewModel: NotesViewModel = viewModel(factory = factory),
    updateNotes: (Notes) -> Unit,
    navigateBack: () -> Unit,
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
                    text = "Detail",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    if (notesViewModel.inputTitle.value.isNotEmpty() && notesViewModel.inputBody.value.isNotEmpty()) {
                        updateNotes(
                            Notes(
                                notesViewModel.note.value!!.id,
                                notesViewModel.inputTitle.value,
                                notesViewModel.inputBody.value,
                                notesViewModel.isFavorite.value
                            )
                        )
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.Done, contentDescription = null)
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
