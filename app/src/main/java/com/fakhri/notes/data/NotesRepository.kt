package com.fakhri.notes.data

import android.provider.ContactsContract.CommonDataKinds.Note
import com.fakhri.notes.data.db.Notes
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun addNotes(notes: Notes)
    suspend fun deleteNotes(notes: Notes)
    suspend fun deleteAll()
    fun getNotesById(noteId: Int):Notes?
    fun getAllNotes(): Flow<List<Notes>>
    suspend fun updateNotes(notes: Notes)
}