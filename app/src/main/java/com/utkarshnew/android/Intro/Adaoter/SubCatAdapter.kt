package com.utkarshnew.android.Intro.Adaoter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.Intro.Activity.IntroActivity
import com.utkarshnew.android.Intro.ItemSelected
import com.utkarshnew.android.Intro.SubCat
import com.utkarshnew.android.R

class SubCatAdapter(
    val context: Context,
    val maincatlist: ArrayList<SubCat>,
    var itemSelected: ItemSelected,
    var maincatpos: Int,
    val mainCatAdapter: MainCatAdapter,
    val categoryAdapter: CategoryAdapter,
    val catposition: Int
) : RecyclerView.Adapter<SubCatAdapter.SubjectItemHolder?>() {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): SubjectItemHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.sub_cat_view, parent, false)
        return SubjectItemHolder(view)
    }


    override fun onBindViewHolder(@NonNull holder: SubjectItemHolder, position: Int) {
        holder.setSingleFAQData(maincatlist, position)
    }

    inner class SubjectItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sub_cat_name: TextView
        private val count: TextView
        private val selectCB: CheckBox
        private val view: View
        fun setSingleFAQData(maincatlist: ArrayList<SubCat>, pos: Int) {
            sub_cat_name.text = maincatlist[pos].name
            count.text = "${pos.plus(1)}. "
            if (pos == maincatlist.size - 1) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }

            selectCB.isChecked = maincatlist[pos].is_subcatselct

            selectCB.setOnClickListener {
                if (maincatlist[pos].is_subcatselct) {
                    maincatlist[pos].is_subcatselct = false
                    var pid = ""
                    for (i in 0 until (context as IntroActivity).maincatlist.size) {
                        if (maincatlist[pos].id == context.maincatlist[i].id) {
                            pid = context.maincatlist[i].parenid
                            context.maincatlist.removeAt(i)
                            break
                        }
                    }
                    categoryAdapter.notifyremove(
                        catposition,
                        (context as IntroActivity).maincatlist,
                        pid
                    )

                } else {
                    maincatlist[pos].is_subcatselct = true
                    (context as IntroActivity).maincatlist.add(maincatlist[pos])
                    categoryAdapter.notifyadd(catposition, (context as IntroActivity).maincatlist)

                }


            }
        }

        init {
            sub_cat_name = itemView.findViewById(R.id.sub_cat_name) as TextView
            selectCB = itemView.findViewById(R.id.selectCB)
            view = itemView.findViewById(R.id.view1)
            count = itemView.findViewById(R.id.count)
        }
    }


    override fun getItemCount(): Int {
        return maincatlist.size

    }


}