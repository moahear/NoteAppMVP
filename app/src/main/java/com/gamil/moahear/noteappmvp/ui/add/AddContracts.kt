package com.gamil.moahear.noteappmvp.ui.add

import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.utils.base.BasePresenter

interface AddContracts {
    interface View {
        fun close()
    }

    interface Presenter : BasePresenter {
        fun saveNote(noteEntity: NoteEntity)
    }
}