package com.lamudi.phonefield.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.lamudi.phonefield.PhoneEditText
import com.lamudi.phonefield.PhoneInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val phoneInputLayout = findViewById<View>(R.id.phone_input_layout) as PhoneInputLayout
        val phoneEditText = findViewById<View>(R.id.edit_text) as PhoneEditText

        val customPhoneInputLayout = CustomPhoneInputLayout(this, "EG")

        val viewGroup = (this
                .findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

        viewGroup.addView(customPhoneInputLayout, 2)


        val button = findViewById<View>(R.id.submit_button) as Button

        phoneInputLayout.setHint(R.string.phone_hint)
        phoneInputLayout.setDefaultCountry("DE")

        phoneEditText.setHint(R.string.phone_hint)
        phoneEditText.setDefaultCountry("FR")

        button.setOnClickListener {
            var valid = true

            if (!phoneInputLayout.isValid) {
                phoneInputLayout.setError(getString(R.string.invalid_phone_number))
                valid = false
            } else {
                phoneInputLayout.setError(null)
            }

            if (!phoneEditText.isValid) {
                phoneEditText.setError(getString(R.string.invalid_phone_number))
                valid = false
            } else {
                phoneEditText.setError(null)
            }

            if (valid) {
                Toast.makeText(this, R.string.valid_phone_number, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show()
            }
        }
    }
}
