package com.lamudi.phonefield

import android.content.Context
import java.util.*

/**
 * Country object that holds the country iso2 code, name, and dial code.
 * @author Ismail Almetwally
 */
data class Country(val code: String, val name: String, val dialCode: Int) {

    val displayName: String
        get() = Locale("", code).getDisplayCountry(Locale.US)

    fun getResId(context: Context): Int {
        val name = String.format("country_flag_%s", code.toLowerCase())
        val resources = context.resources
        return resources.getIdentifier(name, "drawable", context.packageName)
    }
}
