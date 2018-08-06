package com.lamudi.phonefield.sample

import android.content.Context
import android.util.AttributeSet

import com.lamudi.phonefield.PhoneInputLayout

/**
 * Custom PhoneInputLayout that allows users
 * Created by Ismail on 8/30/16.
 */

class CustomPhoneInputLayout : PhoneInputLayout {

    private var mCountryCode: String = ""

    @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context, countryCode: String) : super(context) {
        mCountryCode = countryCode
        init()
    }

    private fun init() {
        setDefaultCountry(mCountryCode)
        setHint(R.string.phone_hint)
    }


}
