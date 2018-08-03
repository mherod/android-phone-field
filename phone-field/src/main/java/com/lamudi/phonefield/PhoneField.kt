package com.lamudi.phonefield

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

abstract class PhoneField @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    var spinner: Spinner? = null
        private set

    var editText: EditText? = null
        private set

    private var mCountry: Country? = null

    private val mPhoneUtil = PhoneNumberUtil.getInstance()

    private var mDefaultCountryPosition = 0

    val isValid: Boolean
        get() = try {
            mPhoneUtil.isValidNumber(parsePhoneNumber(rawInput))
        } catch (e: NumberParseException) {
            false
        }

    var phoneNumber: String
        get() {
            try {
                val number = parsePhoneNumber(rawInput)
                return mPhoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164)
            } catch (ignored: NumberParseException) {
            }
            return rawInput
        }
        set(rawNumber) = try {
            val number = parsePhoneNumber(rawNumber)
            if (mCountry == null || mCountry!!.dialCode != number.countryCode) {
                selectCountry(number.countryCode)
            }
            editText!!.setText(mPhoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.NATIONAL))
        } catch (ignored: NumberParseException) {
        }

    abstract val layoutResId: Int

    private val rawInput: String
        get() = editText?.text.toString()

    init {
        View.inflate(getContext(), this.layoutResId, this)
        this.updateLayoutAttributes()
        this.prepareView()
    }

    /**
     * Prepare view.
     */
    protected open fun prepareView() {

        spinner = findViewWithTag<View>(resources.getString(R.string.com_lamudi_phonefield_flag_spinner)) as Spinner

        editText = findViewWithTag<View>(resources.getString(R.string.com_lamudi_phonefield_edittext)) as EditText

        if (spinner == null || editText == null) {
            throw IllegalStateException("Please provide a valid xml layout")
        }

        val adapter = CountriesAdapter(context, Countries.COUNTRIES)
        spinner!!.setOnTouchListener { v, event ->
            hideKeyboard()
            false
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                var rawNumber = s.toString()
                if (rawNumber.isEmpty()) {
                    spinner!!.setSelection(mDefaultCountryPosition)
                } else {
                    if (rawNumber.startsWith("00")) {
                        rawNumber = rawNumber.replaceFirst("00".toRegex(), "+")
                        editText?.removeTextChangedListener(this)
                        editText?.setText(rawNumber)
                        editText?.addTextChangedListener(this)
                        editText?.setSelection(rawNumber.length)
                    }
                    try {
                        val number = parsePhoneNumber(rawNumber)
                        if (mCountry == null || mCountry!!.dialCode != number.countryCode) {
                            selectCountry(number.countryCode)
                        }
                    } catch (ignored: NumberParseException) {
                    }

                }
            }
        }

        editText?.addTextChangedListener(textWatcher)

        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                mCountry = adapter.getItem(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                mCountry = null
            }
        }

    }

    @Throws(NumberParseException::class)
    private fun parsePhoneNumber(number: String): Phonenumber.PhoneNumber {
        val defaultRegion = if (mCountry != null) mCountry!!.code.toUpperCase() else ""
        return mPhoneUtil.parseAndKeepRawInput(number, defaultRegion)
    }

    fun setDefaultCountry(countryCode: String) {
        for (i in Countries.COUNTRIES.indices) {
            val country = Countries.COUNTRIES[i]
            if (country.code.equals(countryCode, ignoreCase = true)) {
                mCountry = country
                mDefaultCountryPosition = i
                spinner?.setSelection(i)
            }
        }
    }

    private fun selectCountry(dialCode: Int) {
        for (i in Countries.COUNTRIES.indices) {
            val country = Countries.COUNTRIES[i]
            if (country.dialCode == dialCode) {
                mCountry = country
                spinner?.setSelection(i)
            }
        }
    }

    private fun hideKeyboard() {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(editText?.windowToken, 0)
    }

    protected abstract fun updateLayoutAttributes()

    open fun setHint(resId: Int) {
        editText!!.setHint(resId)
    }

    open fun setError(error: String) {
        editText?.error = error
    }

    fun setTextColor(resId: Int) {
        editText?.setTextColor(resId)
    }

}
