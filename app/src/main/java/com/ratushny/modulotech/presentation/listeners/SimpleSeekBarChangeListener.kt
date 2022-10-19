package com.ratushny.modulotech.presentation.listeners

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

open class SimpleSeekBarChangeListener : OnSeekBarChangeListener {
    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {}

    override fun onStartTrackingTouch(seekbar: SeekBar?) {
        // Not used
    }

    override fun onStopTrackingTouch(seekbar: SeekBar?) {
        // Not used
    }
}