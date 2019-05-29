package com.dimchel.aviasalestestapp.utils

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager



object KeyboardUtils {

    fun showKeyboard(context: Context, view: View) {
        view.requestFocus()

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        Handler().postDelayed({
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }, 200)
    }
}