package com.utkarshnew.android.Intro.Fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.utkarshnew.android.Intro.Activity.IntroActivity
import com.utkarshnew.android.R
import com.utkarshnew.android.table.LanguagesTable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LanguageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LanguageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var english_img: ImageView? = null
    var langlistdata: ArrayList<LanguagesTable> = ArrayList()

    var hindi_img: ImageView? = null
    var is_lang_select: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        english_img = view.findViewById(R.id.english_img)
        hindi_img = view.findViewById(R.id.hindi_img)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        langlistdata = (activity as IntroActivity).langlist


        if (!TextUtils.isEmpty((activity as IntroActivity).prefence)) {
            if ((activity as IntroActivity).prefence.contains("#@")) {
                val arr: List<String> = (activity as IntroActivity).prefence.split("#@")
                if (arr[6] == "1") {
                    is_lang_select = true
                    (activity as IntroActivity).is_lang = "1"
                    english_img!!.setImageResource(R.drawable.ic_eng_selected)
                    hindi_img!!.setImageResource(R.drawable.hindi_img)

                } else if (arr[6] == "0") {
                    is_lang_select = true
                    (activity as IntroActivity).is_lang = "1,2"
                    english_img!!.setImageResource(R.drawable.ic_eng_selected)
                    hindi_img!!.setImageResource(R.drawable.ic_hin_selected)
                } else if (arr[6] == "2") {
                    is_lang_select = true
                    (activity as IntroActivity).is_lang = "2"
                    is_lang_select = true
                    english_img!!.setImageResource(R.drawable.english_intro)
                    hindi_img!!.setImageResource(R.drawable.ic_hin_selected)
                }
            } else {
                checkLang()
            }
        } else {
            checkLang()
        }


        english_img!!.setOnClickListener {
            if ((activity as IntroActivity).is_lang.equals("2")) {
                (activity as IntroActivity).is_lang = "1,2"
                is_lang_select = true
                english_img!!.setImageResource(R.drawable.ic_eng_selected)
            } else if ((activity as IntroActivity).is_lang.equals("")) {
                (activity as IntroActivity).is_lang = "1"
                is_lang_select = true
                english_img!!.setImageResource(R.drawable.ic_eng_selected)
            } else if ((activity as IntroActivity).is_lang.equals("1,2")) {
                (activity as IntroActivity).is_lang = "2"
                is_lang_select = true
                english_img!!.setImageResource(R.drawable.english_intro)
                hindi_img!!.setImageResource(R.drawable.ic_hin_selected)
            }

        }
        hindi_img!!.setOnClickListener {
            if ((activity as IntroActivity).is_lang.equals("1")) {
                (activity as IntroActivity).is_lang = "1,2"
                is_lang_select = true
                hindi_img!!.setImageResource(R.drawable.ic_hin_selected)
            } else if ((activity as IntroActivity).is_lang.equals("")) {
                (activity as IntroActivity).is_lang = "2"
                is_lang_select = true
                hindi_img!!.setImageResource(R.drawable.ic_hin_selected)

            } else if ((activity as IntroActivity).is_lang.equals("1,2")) {
                (activity as IntroActivity).is_lang = "1"
                is_lang_select = true
                english_img!!.setImageResource(R.drawable.ic_eng_selected)
                hindi_img!!.setImageResource(R.drawable.hindi_img)
            }

        }
    }

    fun checkLang() {
        when ((activity as IntroActivity).data?.lang) {
            "1" -> {
                is_lang_select = true
                (activity as IntroActivity).is_lang = "1"
                english_img!!.setImageResource(R.drawable.ic_eng_selected)
                hindi_img!!.setImageResource(R.drawable.hindi_img)

            }
            "0" -> {
                is_lang_select = true
                (activity as IntroActivity).is_lang = "1,2"
                english_img!!.setImageResource(R.drawable.ic_eng_selected)
                hindi_img!!.setImageResource(R.drawable.ic_hin_selected)

            }
            "2" -> {
                is_lang_select = true
                (activity as IntroActivity).is_lang = "2"
                is_lang_select = true
                english_img!!.setImageResource(R.drawable.english_intro)
                hindi_img!!.setImageResource(R.drawable.ic_hin_selected)
            }
            else -> {
                is_lang_select = true
                (activity as IntroActivity).is_lang = "1,2"
                english_img!!.setImageResource(R.drawable.ic_eng_selected)
                hindi_img!!.setImageResource(R.drawable.ic_hin_selected)

            }
        }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LanguageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}