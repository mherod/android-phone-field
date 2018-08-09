package com.lamudi.phonefield

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SpinnerAdapter
import android.widget.TextView

/**
 * Adapter for the countries list spinner
 * Created by Ismail on 5/6/16.
 */
class CountriesAdapter(
        context: Context,
        countries: List<Country>
) : ArrayAdapter<Country>(
        context,
        R.layout.item_country,
        R.id.name,
        countries
), SpinnerAdapter {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val country = getItem(position)
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_value, parent, false)
        }
        val imageView = convertView?.findViewById(R.id.flag) as ImageView
        imageView.setImageResource(country!!.getResId(context))
        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_country, parent, false)
            viewHolder = ViewHolder()
            viewHolder.mName = convertView?.findViewById(R.id.name) as TextView
            viewHolder.mDialCode = convertView.findViewById(R.id.dial_code) as TextView
            viewHolder.mFlag = convertView.findViewById(R.id.flag) as ImageView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        val country = getItem(position)
        viewHolder.mFlag?.setImageResource(country!!.getResId(context))
        viewHolder.mName?.text = country.displayName
        viewHolder.mDialCode?.text = country.dialCode.toString()

        return convertView

    }

    private class ViewHolder {
        internal var mName: TextView? = null
        internal var mDialCode: TextView? = null
        internal var mFlag: ImageView? = null
    }
}
