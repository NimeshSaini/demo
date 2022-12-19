package com.utkarshnew.android.Intro.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.Intro.Activity.IntroActivity
import com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter
import com.utkarshnew.android.Intro.SubCat
import com.utkarshnew.android.Intro.SubCatItemSelected
import com.utkarshnew.android.R
import com.utkarshnew.android.table.MasteAllCatTable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChildCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChildCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null




    var sub_cat_recyclerview:RecyclerView?=null
    var chose_txt:TextView?=null
    var masterAllCatTables: ArrayList<MasteAllCatTable> = ArrayList()
    private var selected_master_cat = ArrayList<SubCat>()
    var allSubCategoryAdapter:AllSubCategoryAdapter?=null

    var is_select_course:Boolean ? =false
    var subCat:SubCat?=null

    var  subCatItemSelected:SubCatItemSelected?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        masterAllCatTables = (activity as IntroActivity).masterAllCatTables

        for (masteAllCatTable in masterAllCatTables) {
            if (masteAllCatTable.master_type == (activity as IntroActivity).mastercat!!.catid && masteAllCatTable.parent_id.equals("0", ignoreCase = true)) {
                val subCat=SubCat(masteAllCatTable.id,masteAllCatTable.name,masteAllCatTable.parent_id,masteAllCatTable.master_type)
                selected_master_cat.add(subCat)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sub_cat_recyclerview =view.findViewById(R.id.sub_cat_recyclerview)
        chose_txt =view.findViewById(R.id.chose_txt)

        subCatItemSelected = object : SubCatItemSelected {
            @SuppressLint("NotifyDataSetChanged")
            override fun Selecteditem(item: Int, subcatdata: SubCat) {
                is_select_course =true
                selected_master_cat[item].is_selct =true
                allSubCategoryAdapter!!.randomnumber=0
                allSubCategoryAdapter!!.notifyDataSetChanged()
                subCat =subcatdata
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        if (!is_select_course!!)
        {
            if (!TextUtils.isEmpty( (activity as IntroActivity).prefence)) {
                if ((activity as IntroActivity).prefence.contains("#@")) {
                    val arr: List<String> = (activity as IntroActivity).prefence.split("#@")

                    for (i in 0 until selected_master_cat.size) {
                        if (arr[3] == selected_master_cat[i].id)
                        {
                            is_select_course =true
                            subCat = SubCat(arr[3],arr[2],"","",true)
                            selected_master_cat[i].is_selct =true
                            ((activity) as IntroActivity).subCat = subCat

                        }
                    }
                }
            }

        }
        if ((activity as IntroActivity).mastercat?.catname?.toLowerCase().equals("school"))
        {
            chose_txt?.text = "Select Board"
        }else if ((activity as IntroActivity).mastercat?.catname?.toLowerCase().equals("engineering"))
        {
            chose_txt?.text = "Select Stream"
        }else{
            chose_txt?.text = "Select Course Category"
        }


        allSubCategoryAdapter = AllSubCategoryAdapter(requireActivity(), selected_master_cat,subCatItemSelected!!)
        sub_cat_recyclerview?.apply {
            this.adapter =allSubCategoryAdapter
            this.setHasFixedSize(true)
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChildCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChildCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}