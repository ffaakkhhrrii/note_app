package com.fakhri.notes.di

import android.app.Application
import androidx.room.Room
import com.fakhri.notes.data.NotesRepository
import com.fakhri.notes.data.NotesRepositoryImpl
import com.fakhri.notes.data.db.Notes
import com.fakhri.notes.data.db.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NotesDatabase{
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            "notes_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NotesDatabase): NotesRepository{
        return NotesRepositoryImpl(db.notesDao)
    }
}