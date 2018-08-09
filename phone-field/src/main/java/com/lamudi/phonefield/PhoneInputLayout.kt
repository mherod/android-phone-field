package com.lamudi.phonefield

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout

/**
 * Implementation of PhoneField that uses [TextInputLayout]
 * Created by Ismail on 5/6/16.
 */
open class PhoneInputLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : PhoneField(
        context,
        attrs,
        defStyleAttr
) {

    private var textInputLayout: TextInputLayout? = null

    override fun getLayoutResId(): Int = R.layout.phone_text_input_layout

    override fun updateLayoutAttributes() {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        gravity = Gravity.TOP
        orientation = LinearLayout.HORIZONTAL
    }

    override fun prepareView() {
        super.prepareView()

        textInputLayout = resources.getString(R.string.com_lamudi_phonefield_til_phone)
                .let { findViewWithTag(it) }
    }

    override fun setHint(resId: Int) {
        textInputLayout?.hint = context.getString(resId)
    }

    override fun setError(error: String?) {
        textInputLayout?.isErrorEnabled = error != null && !error.isEmpty()
        textInputLayout?.error = error
    }
}
