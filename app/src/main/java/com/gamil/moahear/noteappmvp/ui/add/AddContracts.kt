package com.gamil.moahear.noteappmvp.ui.add

import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.utils.base.BasePresenter

interface AddContracts {
    interface View {
        fun close()
        fun showNote(noteEntity: NoteEntity)
    }

    interface Presenter : BasePresenter {
        fun saveNote(noteEntity: NoteEntity)
        fun getNote(id: Int)
        fun updateNote(noteEntity: NoteEntity)
    }
}