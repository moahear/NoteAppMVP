package com.gamil.moahear.noteappmvp.data.repository.main

import com.gamil.moahear.noteappmvp.data.database.NoteDao
import javax.inject.Inject

class MainRepository @Inject constructor(private val noteDao: NoteDao) {
    fun getNotes()=noteDao.getNotes()
}