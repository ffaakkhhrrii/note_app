package com.fakhri.notes.data

import com.fakhri.notes.data.db.Notes
import com.fakhri.notes.data.db.NotesDAO
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(private val dao: NotesDAO) : NotesRepository {
    override suspend fun addNotes(notes: Notes) {
        dao.addNotes(notes)
    }

    override suspend fun deleteNotes(notes: Notes) {
        dao.deleteNotes(notes)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override fun getNotesById(noteId: Int): Notes? {
        return dao.getNoteById(noteId)
    }

    override fun getAllNotes(): Flow<List<Notes>> {
        return dao.getAllNotes()
    }

    override suspend fun updateNotes(notes: Notes) {
        dao.updateNote(notes)
    }
}