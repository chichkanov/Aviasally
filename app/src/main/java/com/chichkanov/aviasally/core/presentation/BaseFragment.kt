package com.chichkanov.aviasally.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.chichkanov.aviasally.core.ext.observeValue
import com.chichkanov.diary.base.BaseViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected var disposables = CompositeDisposable()

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    protected fun Disposable.addDisposable() {
        disposables.add(this)
    }

    protected fun showDialog(dialog: DialogFragment, tag: String) {
        if (isDetached || childFragmentManager.findFragmentByTag(tag)?.isAdded == true) {
            return
        }

        dialog.show(childFragmentManager, tag)
    }

    protected fun <T> observe(liveData: LiveData<T>, observer: (T) -> Unit) {
        liveData.observeValue(this, observer)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun provideViewModel(): VM
}