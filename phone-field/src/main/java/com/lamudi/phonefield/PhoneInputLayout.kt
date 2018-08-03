package com.lamudi.phonefield

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup

/**
 * Implementation of PhoneField that uses [TextInputLayout]
 * Created by Ismail on 5/6/16.
 */
open class PhoneInputLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : PhoneField(context, attrs, defStyleAttr) {

    var textInputLayout: TextInputLayout? = null
        private set

    override val layoutResId: Int
        get() = R.layout.phone_text_input_layout

    override fun updateLayoutAttributes() {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        gravity = Gravity.TOP
        orientation = LinearLayout.HORIZONTAL
    }

    override fun prepareView() {
        super.prepareView()
        textInputLayout = findViewWithTag<View>(resources.getString(R.string.com_lamudi_phonefield_til_phone))
    }

    override fun setHint(resId: Int) {
        textInputLayout!!.hint = context.getString(resId)
    }

    override fun setError(error: String?) {
        if (error == null || error.length == 0) {
            textInputLayout!!.isErrorEnabled = false
        } else {
            textInputLayout!!.isErrorEnabled = true
        }
        textInputLayout!!.error = error
    }
}
