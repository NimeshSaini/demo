package com.utkarshnew.android.feeds.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.utkarshnew.android.R
import com.utkarshnew.android.feeds.dataclass.PostType
import com.utkarshnew.android.table.MasteAllCatTable
import com.utkarshnew.android.testmodule.model.TestSection
import java.util.ArrayList

class MainCatAdapter(context: Context, selected_master_cat: ArrayList<MasteAllCatTable>) : ArrayAdapter<MasteAllCatTable?>(context, 0, (selected_master_cat as List<MasteAllCatTable>)) {
    var selected_master_cat = ArrayList<MasteAllCatTable>()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_part, parent, false)
        }
        val tvName = convertView!!.findViewById<View>(R.id.tvName) as TextView
        tvName.text = selected_master_cat[position].name
        return convertView
    }

    init {
        this.selected_master_cat = selected_master_cat
    }
}