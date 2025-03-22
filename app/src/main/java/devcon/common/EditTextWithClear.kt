package devcon.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import devcon.learn.contacts.R

class EditTextWithClear
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = android.R.attr.editTextStyle,
    ) : AppCompatEditText(context, attrs, defStyleAttr) {
        // 클리어 아이콘 Drawable
        private var clearDrawable: Drawable? =
            ContextCompat.getDrawable(context, R.drawable.ic_clear)

        init {
            updateClearIcon()

            addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        updateClearIcon()
                    }
                },
            )

            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP && clearDrawable != null) {
                    if (event.x >= (width - paddingRight - clearDrawable!!.intrinsicWidth)) {
                        text?.clear()
                        performClick()
                        event.action = MotionEvent.ACTION_CANCEL
                        return@setOnTouchListener true
                    }
                }
                false
            }
        }

        private fun updateClearIcon() {
            val shouldShow = text?.isNotEmpty() == true
            setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                if (shouldShow) clearDrawable else null,
                null,
            )
        }

        override fun performClick(): Boolean {
            super.performClick()
            return true
        }
    }
