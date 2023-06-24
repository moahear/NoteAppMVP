package com.gamil.moahear.noteappmvp.utils.base

import io.reactivex.rxjava3.disposables.Disposable

open class BasePresenterImpl:BasePresenter {
    var disposable:Disposable?=null
    override fun onStop() {
        disposable?.let {
            it.dispose()
        }
    }
}