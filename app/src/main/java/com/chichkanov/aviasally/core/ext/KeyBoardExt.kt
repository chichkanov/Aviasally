package com.chichkanov.aviasally.core.ext

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment?.closeKeyboard() {
    this?.activity?.currentFocus?.let { view ->
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun Activity?.closeKeyboard() {
    this?.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun View.closeKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard(customDelay: Long? = null) {
    Handler().postDelayed({
        this.requestFocus()
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }, customDelay ?: 50)
}