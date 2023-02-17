package com.example.recycler_view_practice

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


open class SwipeGesture (context: Context): ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,ItemTouchHelper.LEFT or  ItemTouchHelper.RIGHT){

    val deleteColor = ContextCompat.getColor(context, android.R.color.holo_red_dark)
    val archiveColor = ContextCompat.getColor(context, android.R.color.holo_green_dark)

    val deleteIcon = R.drawable.ic_baseline_delete
    val archiveIcon = R.drawable.ic_baseline_archive



    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean)
    {
        RecyclerViewSwipeDecorator.Builder(c,recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeRightBackgroundColor(archiveColor)
            .addSwipeLeftActionIcon(deleteIcon)
            .addSwipeRightActionIcon(archiveIcon)
            .create()
            .decorate()

        super.onChildDraw(c!!, recyclerView!!,
            viewHolder!!, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }



}