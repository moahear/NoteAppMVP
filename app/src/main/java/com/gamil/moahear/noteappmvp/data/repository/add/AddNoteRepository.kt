package com.gamil.moahear.noteappmvp.data.repository.add

import com.gamil.moahear.noteappmvp.data.database.NoteDao
import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import javax.inject.Inject

class AddNoteRepository @Inject constructor(private val noteDao: NoteDao) {
    fun saveNote(noteEntity: NoteEntity) = noteDao.saveNote(noteEntity)
    fun getNote(noteId: Int) = noteDao.getNote(noteId)
    fun updateNote(noteEntity: NoteEntity) = noteDao.updateNote(noteEntity)
}