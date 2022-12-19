package com.utkarshnew.android.Intro.Adaoter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.utkarshnew.android.Intro.Activity.IntroActivity
import com.utkarshnew.android.Intro.ItemSelected
import com.utkarshnew.android.Intro.Mastercat
import com.utkarshnew.android.Intro.SubCat
import com.utkarshnew.android.R

class MainCatAdapter(
    val context: Context,
    val maincatlist: ArrayList<SubCat>,
    var itemSelected: ItemSelected,
    val categoryAdapter: CategoryAdapter,
    val catpos: Int
) :
    RecyclerView.Adapter<MainCatAdapter.SubjectItemHolder?>() {
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): SubjectItemHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.main_cat_view, parent, false)
        return SubjectItemHolder(view)
    }


    override fun onBindViewHolder(@NonNull holder: SubjectItemHolder, position: Int) {
        holder.setSingleFAQData(maincatlist, position)
    }

    inner class SubjectItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val category_name: TextView
        private val dropDownIV: ImageView
        private val parentLL: LinearLayout
        private val view: View
        private val category_count: TextView
        private val sub_cat_recycler: RecyclerView
        fun setSingleFAQData(maincatlist: ArrayList<SubCat>, pos: Int) {
            category_name.text = maincatlist[pos].name
            if (pos == maincatlist.size - 1) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }

            val selectedsub_all_cat = ArrayList<SubCat>()
            for (masteAllCatTable1 in (context as IntroActivity).masterAllCatTables) {
                if (maincatlist[pos].id.equals(masteAllCatTable1.parent_id, ignoreCase = true)) {
                    val subCat = SubCat(
                        masteAllCatTable1.id,
                        masteAllCatTable1.name,
                        masteAllCatTable1.parent_id,
                        masteAllCatTable1.master_type
                    )
                    selectedsub_all_cat.add(subCat)
                }
            }
            var count = 0
            for (i in 0 until selectedsub_all_cat.size) {
                for (j in 0 until context.maincatlist.size) {
                    if (context.maincatlist[j].id == selectedsub_all_cat[i].id) {
                        selectedsub_all_cat[i].is_subcatselct = true
                        count++
                    }
                }
            }
            if (count > 0) {
                category_count.visibility = View.VISIBLE
                category_count.text = "($count)"
            } else {
                category_count.visibility = View.INVISIBLE
            }
            sub_cat_recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            sub_cat_recycler.adapter = SubCatAdapter(
                context,
                selectedsub_all_cat,
                itemSelected,
                pos,
                this@MainCatAdapter,
                categoryAdapter,
                catpos
            )
            sub_cat_recycler.isNestedScrollingEnabled = false


            if (maincatlist[pos].is_maincatselct) {
                sub_cat_recycler.visibility = View.VISIBLE
                dropDownIV.setImageResource(R.mipmap.up_black)
            } else {
                sub_cat_recycler.visibility = View.GONE
                dropDownIV.setImageResource(R.mipmap.down_black)
            }

            dropDownIV.setOnClickListener {
                if (maincatlist[pos].is_maincatselct) {
                    maincatlist[pos].is_maincatselct = false
                    sub_cat_recycler.visibility = View.GONE
                    dropDownIV.setImageResource(R.mipmap.down_black)
                } else {
                    maincatlist[pos].is_maincatselct = true
                    sub_cat_recycler.visibility = View.VISIBLE
                    dropDownIV.setImageResource(R.mipmap.up_black)

                }
                Log.d("asdfghjkl", "setSingleFAQData: ${Gson().toJson(maincatlist[pos])}")
                Log.d("asdfghjkl", "setSingleFAQData: ${Gson().toJson(context.maincatlist)}")


            }
            parentLL.setOnClickListener {
                if (maincatlist[pos].is_maincatselct) {
                    maincatlist[pos].is_maincatselct = false
                    sub_cat_recycler.visibility = View.GONE
                    dropDownIV.setImageResource(R.mipmap.down_black)
                } else {
                    maincatlist[pos].is_maincatselct = true
                    sub_cat_recycler.visibility = View.VISIBLE
                    dropDownIV.setImageResource(R.mipmap.up_black)

                }
                Log.d("asdfghjkl", "setSingleFAQData: ${Gson().toJson(maincatlist[pos])}")
                Log.d("asdfghjkl", "setSingleFAQData: ${Gson().toJson(context.maincatlist)}")


            }
        }

        init {
            category_name = itemView.findViewById(R.id.category_name) as TextView
            sub_cat_recycler = itemView.findViewById(R.id.sub_cat_recycler)
            category_count = itemView.findViewById(R.id.category_count)
            dropDownIV = itemView.findViewById(R.id.dropDownIV)
            parentLL = itemView.findViewById(R.id.parentLL)
            view = itemView.findViewById(R.id.view1)
        }
    }


    override fun getItemCount(): Int {
        return maincatlist.size

    }


}