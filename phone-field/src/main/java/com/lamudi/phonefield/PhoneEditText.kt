package com.lamudi.phonefield

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout

class PhoneEditText @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : PhoneField(
        context,
        attrs,
        defStyleAttr
) {

    override val layoutResId: Int = R.layout.phone_edit_text

    override fun updateLayoutAttributes() {

        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        gravity = Gravity.CENTER_VERTICAL
        orientation = LinearLayout.HORIZONTAL

        setPadding(0, context.resources.getDimensionPixelSize(R.dimen.padding_large), 0, 0)
    }
}
