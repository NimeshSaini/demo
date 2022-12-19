package com.utkarshnew.android.Intro.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.Intro.Activity.IntroActivity
import com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter
import com.utkarshnew.android.Intro.SubCat
import com.utkarshnew.android.Intro.SubCatItemSelected
import com.utkarshnew.android.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [All_ChildCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class All_ChildCategoryFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    var sub_cat_recyclerview:RecyclerView?=null
    var chose_txt:TextView?=null
    var subCatItemSelected: SubCatItemSelected?=null
    private var selectedsub_all_cat = ArrayList<SubCat>()
    var allsubCat:SubCat?=null
    var allSubCategoryAdapter:AllSubCategoryAdapter?=null
    var is_select_allsubcat:Boolean ? =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        for (masteAllCatTable1 in (activity as IntroActivity).masterAllCatTables) {
            if ((activity as IntroActivity).subCat!!.id.equals(masteAllCatTable1.parent_id, ignoreCase = true)) {
                val subCat=SubCat(masteAllCatTable1.id,masteAllCatTable1.name,masteAllCatTable1.parent_id,masteAllCatTable1.master_type)
                selectedsub_all_cat.add(subCat)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_child_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sub_cat_recyclerview =view.findViewById(R.id.sub_cat_recyclerview)
        chose_txt =view.findViewById(R.id.chose_txt)



        subCatItemSelected = object : SubCatItemSelected {
            @SuppressLint("NotifyDataSetChanged")
            override fun Selecteditem(item: Int, subcatdata: SubCat) {
                allsubCat =subcatdata
                is_select_allsubcat =true
                selectedsub_all_cat[item].is_selct =true
                allSubCategoryAdapter!!.randomnumber=0

                allSubCategoryAdapter!!.notifyDataSetChanged()
            }
        }



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        if (!TextUtils.isEmpty( (activity as IntroActivity).prefence)) {
            if ((activity as IntroActivity).prefence.contains("#@")) {
                val arr: List<String> = (activity as IntroActivity).prefence.split("#@")

                for (i in 0 until selectedsub_all_cat.size) {
                    if (arr[5] == selectedsub_all_cat[i].id)
                    {
                        is_select_allsubcat =true
                        allsubCat = SubCat(arr[5],arr[4],"","",true)
                        selectedsub_all_cat[i].is_selct =true
                    }
                }

            }
          /*  if ((activity as IntroActivity).mastercat?.catid.equals("3"))
            {
                chose_txt?.text = "Select Class Name"
            }else{
                chose_txt?.text = "Select Course Name"
            }*/
            if ((activity as IntroActivity).mastercat?.catname?.toLowerCase().equals("school"))
            {
                chose_txt?.text = "Select Class Name"
            }else if ((activity as IntroActivity).mastercat?.catname?.toLowerCase().equals("engineering"))
            {
                chose_txt?.text = "Select Sub Stream"
            }else{
                chose_txt?.text = "Select Course Name"
            }
        }


        allSubCategoryAdapter = AllSubCategoryAdapter(requireActivity(), selectedsub_all_cat,subCatItemSelected!!)
        sub_cat_recyclerview?.apply {
            this.adapter =allSubCategoryAdapter
            this.setHasFixedSize(true)
        }



    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            All_ChildCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}