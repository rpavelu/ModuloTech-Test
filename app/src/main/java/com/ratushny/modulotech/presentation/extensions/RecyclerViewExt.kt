package com.ratushny.modulotech.presentation.extensions

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ratushny.modulotech.presentation.common.SwipeToDeleteTouchHelper

fun RecyclerView.attachSwipeToDelete(swipeToDelete: SwipeToDeleteTouchHelper) {
    val itemTouchHelper = ItemTouchHelper(swipeToDelete)
    itemTouchHelper.attachToRecyclerView(this)
}