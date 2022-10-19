package com.ratushny.modulotech.presentation.extensions

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ratushny.modulotech.presentation.common.SwipeToDeleteTouchHelper

fun <T> RecyclerView.attachSwipeToDelete(itemProvider: (Int) -> T, doOnSwipe: (T) -> Unit) {

    val swipeTouchHelper = object : SwipeToDeleteTouchHelper(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.absoluteAdapterPosition
            doOnSwipe(itemProvider(position))
        }
    }
    val itemTouchHelper = ItemTouchHelper(swipeTouchHelper)
    itemTouchHelper.attachToRecyclerView(this)
}