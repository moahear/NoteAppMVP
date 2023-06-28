package com.gamil.moahear.noteappmvp.data.repository.main

import com.gamil.moahear.noteappmvp.data.database.NoteDao
import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val noteDao: NoteDao) {
    fun getNotes()=noteDao.getNotes()
    fun deleteNote(noteEntity: NoteEntity)=noteDao.deleteNote(noteEntity)
    fun filterNotesWithPriority(priority:String)=noteDao.filterNotesWithPriority(priority)

}