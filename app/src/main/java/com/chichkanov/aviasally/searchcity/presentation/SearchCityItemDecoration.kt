package com.chichkanov.aviasally.searchcity.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chichkanov.aviasally.R
import com.chichkanov.aviasally.core.ext.getColorCompat

class SearchCityItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        color = context.getColorCompat(R.color.n2_5)
    }

    private val paintAlpha = paint.alpha
    private val dividerHeight = context.resources.getDimension(R.dimen.divider_height)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            with(parent.getChildAt(i)) {
                if (isNeedDivider(parent, this)) {
                    // Для плавного изменения цвета дивайдера
                    paint.alpha = (alpha * paintAlpha).toInt()
                    c.drawRect(
                        left.toFloat(),
                        top.toFloat(),
                        right.toFloat(),
                        top.toFloat() + dividerHeight,
                        paint
                    )
                }
            }
        }
    }

    private fun isNeedDivider(parent: RecyclerView, view: View): Boolean {
        val position = parent.getChildAdapterPosition(view)
        return position != 0 && position != RecyclerView.NO_POSITION
    }
}