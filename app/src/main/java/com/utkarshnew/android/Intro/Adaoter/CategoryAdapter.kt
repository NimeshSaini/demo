package com.utkarshnew.android.Intro.Adaoter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.utkarshnew.android.Intro.Activity.IntroActivity
import com.utkarshnew.android.Intro.ItemSelected
import com.utkarshnew.android.Intro.Mastercat
import com.utkarshnew.android.Intro.SubCat
import com.utkarshnew.android.R
import com.utkarshnew.android.table.MasteAllCatTable

class CategoryAdapter(
    var context: Context,
    var mastercatlist: ArrayList<Mastercat>,
    var itemSelected: ItemSelected
) : RecyclerView.Adapter<CategoryAdapter.CategoryAdapterVh>() {

    var list = (context as IntroActivity).maincatlist
    var pid = ""
    var randomnumber = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterVh {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_adapter, parent, false)
        return CategoryAdapterVh(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CategoryAdapterVh, position: Int) {
        holder.category_name.text = mastercatlist[position].catname
        if ((position % 4) == 0) {
            holder.dropDownIV.setColorFilter(ContextCompat.getColor(context, R.color.whie), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.school_cat.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_pink))
            holder.category_name.setTextColor(context.resources.getColor(R.color.whie))
        } else if (position % 4 == 1) {
            holder.dropDownIV.setColorFilter(ContextCompat.getColor(context, R.color.whie), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.school_cat.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_blue))
            holder.category_name.setTextColor(context.resources.getColor(R.color.whie))
        } else if (position % 4 == 2) {
            holder.dropDownIV.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);

            holder.school_cat.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_yello))
            holder.category_name.setTextColor(context.resources.getColor(R.color.black))
        } else if (position % 4 == 3) {
            holder.dropDownIV.setColorFilter(ContextCompat.getColor(context, R.color.whie), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.school_cat.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_green))
            holder.category_name.setTextColor(context.resources.getColor(R.color.whie))
        }

        if (mastercatlist[position].is_expand_maincat) {
            holder.lowerViewItem_maincat.visibility = View.VISIBLE
            holder.dropDownIV.setImageResource(R.drawable.up_arrow_black)
        } else {
            holder.lowerViewItem_maincat.visibility = View.GONE
            holder.dropDownIV.setImageResource(R.drawable.down_arrow_black)
        }



        holder.dropDownIV.setOnClickListener {
            if (mastercatlist[position].is_expand_maincat) {
                mastercatlist[position].is_expand_maincat = false
                holder.lowerViewItem_maincat.visibility = View.GONE
                holder.dropDownIV.setImageResource(R.mipmap.down_black)
            } else {
                mastercatlist[position].is_expand_maincat = true
                holder.lowerViewItem_maincat.visibility = View.VISIBLE
                holder.dropDownIV.setImageResource(R.mipmap.up_black)
            }
        }
        holder.school_cat.setOnClickListener {
            if (mastercatlist[position].is_expand_maincat) {
                mastercatlist[position].is_expand_maincat = false
                holder.lowerViewItem_maincat.visibility = View.GONE
                holder.dropDownIV.setImageResource(R.mipmap.down_black)
            } else {
                mastercatlist[position].is_expand_maincat = true
                holder.lowerViewItem_maincat.visibility = View.VISIBLE
                holder.dropDownIV.setImageResource(R.mipmap.up_black)
            }
        }

        val selected_master_cat = ArrayList<SubCat>()
        val masterAllCatTables: ArrayList<MasteAllCatTable> =
            (context as IntroActivity).masterAllCatTables
        for (masteAllCatTable in masterAllCatTables) {
            if (masteAllCatTable.master_type == mastercatlist[position].catid && masteAllCatTable.parent_id.equals(
                    "0",
                    ignoreCase = true
                )
            ) {
                val subCat = SubCat(
                    masteAllCatTable.id,
                    masteAllCatTable.name,
                    masteAllCatTable.parent_id,
                    masteAllCatTable.master_type
                )
                if (pid.equals(masteAllCatTable.id)) {
                    subCat.is_maincatselct = true
                }
                selected_master_cat.add(subCat)
            }
        }

       /* var count = 0;
        for (i in 0 until selected_master_cat.size) {
            for (j in 0 until list.size) {
                if (list[j].parenid == selected_master_cat[i].id) {
                    count++
                    break
                }
            }
        }*/

        val selectedsub_all_cat = ArrayList<SubCat>()
        for (masteAllCatTable1 in (context as IntroActivity).masterAllCatTables) {
            for (subcat in selected_master_cat)
            {
                if (subcat.id.equals(masteAllCatTable1.parent_id, ignoreCase = true)) {
                    val subCat = SubCat(
                        masteAllCatTable1.id,
                        masteAllCatTable1.name,
                        masteAllCatTable1.parent_id,
                        masteAllCatTable1.master_type
                    )
                    selectedsub_all_cat.add(subCat)
                }
            }

        }
        var count = 0
        for (i in 0 until selectedsub_all_cat.size) {
            for (j in 0 until (context as IntroActivity).maincatlist.size) {
                if ((context as IntroActivity).maincatlist[j].id == selectedsub_all_cat[i].id) {
                    selectedsub_all_cat[i].is_subcatselct = true
                    count++
                }
            }
        }




        if (count > 0) {
            holder.category_count.visibility = View.VISIBLE
            if (position % 4 == 2) {
                holder.category_count.setTextColor(context.resources.getColor(R.color.black))
            } else {
                holder.category_count.setTextColor(context.resources.getColor(R.color.colorPrimary))

            }
            holder.category_count.text = "($count)"
        } else {
            holder.category_count.visibility = View.INVISIBLE
        }

        holder.main_cat_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.main_cat_recycler.adapter = MainCatAdapter(
            context,
            selected_master_cat,
            itemSelected,
            this@CategoryAdapter,
            position
        )
        holder.main_cat_recycler.isNestedScrollingEnabled = false
    }

    override fun getItemCount(): Int {
        return mastercatlist.size
    }

    fun notifyadd(catposition: Int, maincatlist: ArrayList<SubCat>) {
        list = maincatlist
        pid = list[list.size - 1].parenid

        notifyItemChanged(catposition)

    }

    fun notifyremove(catposition: Int, maincatlist: ArrayList<SubCat>, pid: String) {
        list = maincatlist
        this.pid = pid
        notifyItemChanged(catposition)

    }

    inner class CategoryAdapterVh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var school_cat: RelativeLayout
        var parentLL: LinearLayout
        var lowerViewItem_maincat: LinearLayout
        var dropDownIV: ImageView
        var category_name: TextView
        var category_count: TextView
        var main_cat_recycler: RecyclerView

        init {
            school_cat = itemView.findViewById(R.id.school_cat)
            dropDownIV = itemView.findViewById(R.id.dropDownIV)
            parentLL = itemView.findViewById(R.id.parentLL)
            main_cat_recycler = itemView.findViewById(R.id.main_cat_recycler)
            lowerViewItem_maincat = itemView.findViewById(R.id.lowerViewItem)
            category_name = itemView.findViewById(R.id.category_name)
            category_count = itemView.findViewById(R.id.category_count)

        }
    }
}