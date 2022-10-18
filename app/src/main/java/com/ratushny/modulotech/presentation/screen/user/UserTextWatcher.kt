package com.ratushny.modulotech.presentation.screen.user

import android.text.Editable
import android.text.TextWatcher

open class UserTextWatcher : TextWatcher {
    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
        // Not used
    }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(text.toString())
    }

    override fun afterTextChanged(text: Editable?) {
        // Not used
    }

    open fun onTextChanged(text: String) {

    }
}