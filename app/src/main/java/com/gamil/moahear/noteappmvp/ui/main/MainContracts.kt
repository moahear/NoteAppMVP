package com.gamil.moahear.noteappmvp.ui.main

import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.utils.base.BasePresenter

interface MainContracts {
    interface View{
     fun showNotes(notes:List<NoteEntity>)
     fun showEmpty()
    }
    interface Presenter:BasePresenter{
     fun getNotes()
    }
}