package com.gamil.moahear.noteappmvp.ui.main

import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.utils.base.BasePresenter

interface MainContracts {
    interface View {
        fun showNotes(notes: List<NoteEntity>)
        fun showEmpty()
        fun showDeleteMessage()
    }

    interface Presenter : BasePresenter {
        fun getNotes()
        fun deleteNote(noteEntity: NoteEntity)

        fun filterNotesWithPriority(priority:String)
    }
}