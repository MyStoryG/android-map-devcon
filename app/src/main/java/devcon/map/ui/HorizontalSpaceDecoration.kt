package devcon.map.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpaceDecoration(
    private val paddingLeft: Float = 0F,
    private val paddingRight: Float = 0F,
    private val marginLeft: Float = 0F,
    private val marginRight: Float = 0F,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val lastIndex = parent.adapter?.itemCount?.minus(1) ?: 0

        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.left += marginLeft.toInt()
                outRect.right += paddingRight.toInt()
            }

            lastIndex -> {
                outRect.left += paddingLeft.toInt()
                outRect.right += marginRight.toInt()
            }

            else -> {
                outRect.left += paddingLeft.toInt()
                outRect.right += paddingRight.toInt()
            }
        }
    }
}
