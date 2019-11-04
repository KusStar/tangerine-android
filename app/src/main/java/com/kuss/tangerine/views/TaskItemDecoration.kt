package com.kuss.tangerine.views

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.Rect
import android.view.View
import com.kuss.tangerine.R


class TaskItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private var divider: Int? = null
    init {
        divider = context.getResources().getDimensionPixelSize(R.dimen.divider);
    }
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        divider?.let {
            outRect.bottom = it //相当于 设置 bottom padding
        }

    }
}