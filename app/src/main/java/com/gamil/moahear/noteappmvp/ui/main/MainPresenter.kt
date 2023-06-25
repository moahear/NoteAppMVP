package com.gamil.moahear.noteappmvp.ui.main

import com.gamil.moahear.noteappmvp.data.repository.main.MainRepository
import com.gamil.moahear.noteappmvp.utils.base.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mainRepository: MainRepository,private val view:MainContracts.View):MainContracts.Presenter,BasePresenterImpl() {
    override fun getNotes() {
        disposable=mainRepository.getNotes().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.showNotes(it)
                }
                else{
                    view.showEmpty()
                }
            }
    }

}