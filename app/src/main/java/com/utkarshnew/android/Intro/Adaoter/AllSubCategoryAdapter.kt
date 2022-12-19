package com.utkarshnew.android.Intro.Adaoter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.Intro.SubCat
import com.utkarshnew.android.Intro.SubCatItemSelected
import com.utkarshnew.android.R

class AllSubCategoryAdapter(
    var context: Context,
    var mastercatlist: ArrayList<SubCat>,
    var subCatItemSelected: SubCatItemSelected
) : RecyclerView.Adapter<AllSubCategoryAdapter.CategoryAdapterVh>() {
    var randomnumber: Int = 0
    var colorchnage = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterVh {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_adapter, parent, false)
        return CategoryAdapterVh(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CategoryAdapterVh, position: Int) {
        holder.category_name.text = mastercatlist[position].name
        if ((position % 4) == 0) {
            holder.school_cat.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_pink))
            holder.category_name.setTextColor(context.resources.getColor(R.color.whie))
        } else if (position % 4 == 1) {
            holder.school_cat.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_blue))
            holder.category_name.setTextColor(context.resources.getColor(R.color.whie))
        } else if (position % 4 == 2) {
            holder.school_cat.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_yello))
            holder.category_name.setTextColor(context.resources.getColor(R.color.black))
        } else if (position % 4 == 3) {
            holder.school_cat.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_green))
            holder.category_name.setTextColor(context.resources.getColor(R.color.whie))
        }

      /*  if (mastercatlist[position].is_selct) {
            holder.tick_school.visibility = View.VISIBLE
        } else {
            holder.tick_school.visibility = View.GONE
        }*/
        holder.school_cat.setOnClickListener {
            for (i in 0 until mastercatlist.size) {
                if (mastercatlist[i].is_selct) {
                    mastercatlist[i].is_selct = false
                }
            }
            subCatItemSelected.Selecteditem(position, mastercatlist[position])

        }
    }

    override fun getItemCount(): Int {
        return mastercatlist.size
    }

    inner class CategoryAdapterVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var school_cat: RelativeLayout
      //  var tick_school: RelativeLayout
        var category_name: TextView

        init {
            school_cat = itemView.findViewById(R.id.school_cat)
          //  tick_school = itemView.findViewById(R.id.tick_school)
            category_name = itemView.findViewById(R.id.category_name)

        }
    }
}