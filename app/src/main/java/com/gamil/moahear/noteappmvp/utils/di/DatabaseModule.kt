package com.gamil.moahear.noteappmvp.utils.di

import android.content.Context
import androidx.room.Room
import com.gamil.moahear.noteappmvp.data.database.NoteDao
import com.gamil.moahear.noteappmvp.data.database.NoteDatabase
import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, Constants.NOTES_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao =
        noteDatabase.noteDao()

    @Provides
    @Singleton
    fun provideEntity(): NoteEntity = NoteEntity()
}