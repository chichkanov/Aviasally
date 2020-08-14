package com.chichkanov.diary.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    protected fun Disposable.addDisposable() {
        disposables.add(this)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}