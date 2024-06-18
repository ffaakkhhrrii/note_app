package com.fakhri.notes.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {
    @Insert
    suspend fun addNotes(notes: Notes)

    @Delete
    suspend fun deleteNotes(notes: Notes)

    @Query("DELETE FROM tb_notes")
    suspend fun deleteAll()

    @Query("SELECT * FROM tb_notes")
    fun getAllNotes():Flow<List<Notes>>

    // Query to get a note by its ID
    @Query("SELECT * FROM tb_notes WHERE id = :noteId")
    fun getNoteById(noteId: Int): Notes?

    @Update
    suspend fun updateNote(notes: Notes)

    @Query("SELECT * FROM tb_notes WHERE isFavorite = :isFavorite")
    fun getFavoriteNotes(isFavorite: Boolean): Flow<List<Notes>>

    @Query("UPDATE tb_notes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int,isFavorite: Boolean)

}