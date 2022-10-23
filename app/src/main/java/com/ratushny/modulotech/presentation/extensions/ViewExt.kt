package com.ratushny.modulotech.presentation.extensions

import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.doOnTextChanged(action: (String) -> Unit) {
    this.addTextChangedListener(onTextChanged = { text, _, _, _ ->
        action(text?.toString() ?: "")
    })
}

fun TextInputEditText.updateTextIfNeeded(newText: String) {
    if (this.text?.toString() != newText) {
        val selectionStart = this.selectionStart
        val selectionEnd = this.selectionEnd
        setText(newText)
        setSelection(
            selectionStart.coerceAtMost(newText.length),
            selectionEnd.coerceAtMost(newText.length)
        )
    }
}

fun SeekBar.doOnProgressChanged(action: (Int) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
            action(progress)
        }

        override fun onStartTrackingTouch(seekbar: SeekBar?) {
            // Not used
        }

        override fun onStopTrackingTouch(seekbar: SeekBar?) {
            // Not used
        }
    })
}