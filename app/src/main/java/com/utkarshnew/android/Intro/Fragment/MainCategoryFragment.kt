package com.utkarshnew.android.Intro.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.Intro.Activity.IntroActivity
import com.utkarshnew.android.Intro.Adaoter.CategoryAdapter
import com.utkarshnew.android.Intro.ItemSelected
import com.utkarshnew.android.Intro.Mastercat
import com.utkarshnew.android.Intro.SubCat
import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.SharedPreference

private const val ARG_PARAM1 = "masterjsonlist"
private const val ARG_PARAM2 = "param2"

class MainCategoryFragment() : Fragment() {

    var school_cat: RelativeLayout? = null
    var is_select_main_cat: Boolean? = false
    var mastercat: Mastercat? = null
    var tick_school: RelativeLayout? = null
    var tick_neet: RelativeLayout? = null
    var competate_cat: RelativeLayout? = null
    var main_cat_recyclerview: RecyclerView? = null
    var itemSelected: ItemSelected? = null
    var neet_cat: RelativeLayout? = null
    var chose_txt: TextView? = null
    private var param1: String? = null
    private var param2: String? = null
    var mastercatlist: ArrayList<Mastercat> = ArrayList()

    var categoryAdapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1, "")
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_cat_recyclerview = view.findViewById(R.id.main_cat_recyclerview)
        chose_txt = view.findViewById(R.id.chose_txt)


        itemSelected = object : ItemSelected {
            override fun Selecteditem(item: Int, master: Mastercat) {

                is_select_main_cat = true
                mastercatlist[item].is_select = true
                mastercat = master
                categoryAdapter!!.randomnumber = 0
                categoryAdapter!!.notifyDataSetChanged()
            }

            override fun Selectedsubcat(item: Int, sucat: SubCat, type: String) {

            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mastercatlist = (activity as IntroActivity).mastercatlist


        if ((activity as IntroActivity).maincatlist.size>0)
        {
            for (i in 0 until mastercatlist.size)
            {
                for (j in 0 until (activity as IntroActivity).maincatlist.size)
                {
                    if (mastercatlist[i].catid==(activity as IntroActivity).maincatlist[j].mastertype)
                    {
                       // mastercatlist[i].is_expand_maincat =true
                    }
                }
            }


        }else
        if (!is_select_main_cat!!) {
            if (!TextUtils.isEmpty((activity as IntroActivity).prefence)) {
                if ((activity as IntroActivity).prefence.contains("#@")) {
                    val arr: List<String> = (activity as IntroActivity).prefence.split("#@")

                    for (i in 0 until mastercatlist.size) {
                        if (arr[1] == mastercatlist[i].catid) {
                            mastercat = Mastercat(arr[1], arr[0], true)
                            is_select_main_cat = true
                            mastercatlist[i].is_select = true
                            mastercatlist[i].is_expand_maincat = true
                            ((activity) as IntroActivity).mastercat = mastercat
                        }
                    }

                }
            }


        }


        categoryAdapter = CategoryAdapter(requireActivity(), mastercatlist, itemSelected!!)
        main_cat_recyclerview?.apply {
            this.adapter = categoryAdapter
            this.setHasFixedSize(true)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}