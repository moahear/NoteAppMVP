package com.gamil.moahear.noteappmvp.ui.add

import com.gamil.moahear.noteappmvp.data.model.NoteEntity
import com.gamil.moahear.noteappmvp.data.repository.add.AddNoteRepository
import com.gamil.moahear.noteappmvp.utils.base.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AddPresenter @Inject constructor(
    private val addNoteRepository: AddNoteRepository,
    private val view: AddContracts.View
) : AddContracts.Presenter, BasePresenterImpl() {
    override fun saveNote(noteEntity: NoteEntity) {
        disposable = addNoteRepository.saveNote(noteEntity).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                view.close()
            }
    }

    override fun getNote(noteId: Int) {
        disposable = addNoteRepository.getNote(noteId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showNote(it)
            }
    }

    override fun updateNote(noteEntity: NoteEntity) {
        disposable = addNoteRepository.updateNote(noteEntity).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.close()
            }
    }


}