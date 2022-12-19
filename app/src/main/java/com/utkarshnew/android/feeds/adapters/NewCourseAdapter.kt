package com.utkarshnew.android.feeds.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.Spanned
import android.text.TextUtils
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.utkarshnew.android.courses.Activity.CourseActivity
import com.utkarshnew.android.feeds.dataclass.NewCourseData
import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.Const
import com.utkarshnew.android.Utils.Helper

class NewCourseAdapter : RecyclerView.Adapter<NewCourseAdapter.NewCourseVH>() {

    var new_course_data: List<NewCourseData>? = emptyList()
    var context: Context?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCourseVH {
        val view = LayoutInflater.from(context).inflate(R.layout.new_course_adapter, parent, false)
        return NewCourseVH(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: NewCourseVH, position: Int) {
        val newCourseData =new_course_data!![position]
        if (!TextUtils.isEmpty(newCourseData.cover_image)) {
            Helper.setThumbnailImage(context,newCourseData.cover_image, context!!.getResources().getDrawable(R.mipmap.book_placeholder), holder.course_thumbnail)
        } else {
            holder.course_thumbnail.setImageResource(R.mipmap.book_placeholder)
        }
        holder.course_name.text = (newCourseData.title)


        if (newCourseData.validity.equals("0", ignoreCase = true)) {
            holder.validityTextTV.setVisibility(View.GONE)
        } else {
            holder.validityTextTV.setVisibility(View.VISIBLE)
        }
        holder.parent_layout.setOnClickListener{
            val newCourseData =new_course_data!![position]
            val courseList = Intent(context, CourseActivity::class.java)
                courseList.putExtra(Const.FRAG_TYPE, Const.SINGLE_STUDY)
                courseList.putExtra(Const.COURSE_ID_MAIN, newCourseData.id)
                courseList.putExtra(Const.COURSE_PARENT_ID, "")
                courseList.putExtra(Const.IS_COMBO, false)
                courseList.putExtra("course_name", newCourseData.title)
                Helper.gotoActivity(courseList, context as Activity)
        }
        if (newCourseData.course_sp.equals("0", ignoreCase = true)) {
            holder.price.setText(context!!.getResources().getString(R.string.free))
            holder.price.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START)
            holder.validityTextTV.setText(String.format("%s %s", context!!.getResources().getString(R.string.validity), newCourseData.validity))
            holder.mrpCutTV.setVisibility(View.GONE)
        } else {
            if (newCourseData.course_sp.equals(newCourseData.mrp, ignoreCase = true)) {
                holder.mrpCutTV.setVisibility(View.GONE)
                holder.validityTextTV.setText(String.format("%s %s", context!!.getResources().getString(R.string.validity), newCourseData.validity, newCourseData.validity.equals("0", ignoreCase = true)))
                holder.price.setText(context!!.getResources().getString(R.string.rs).toString() + "" + newCourseData.mrp + "/-")
            } else {
               holder.price.setText(String.format("%s %s %s", context!!.getResources().getString(R.string.rs), newCourseData.course_sp, "/-"))
                holder.mrpCutTV.setText(String.format("%s %s %s", context!!.getResources().getString(R.string.rs), newCourseData.mrp, "/-"), TextView.BufferType.SPANNABLE)
                val STRIKE_THROUGH_SPAN = StrikethroughSpan()
                val spannable = holder.mrpCutTV.getText() as Spannable
                holder.mrpCutTV.setVisibility(View.VISIBLE)
                spannable.setSpan(STRIKE_THROUGH_SPAN, 2, (newCourseData.mrp).length + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                holder.validityTextTV.setText(String.format("%s %s", context!!.getResources().getString(R.string.validity), newCourseData.validity, newCourseData.validity.equals("0", ignoreCase = true)))
            }
        }
    }

    override fun getItemCount(): Int {
        return new_course_data!!.size
    }

    fun updateItems(data: List<NewCourseData>?, contxt: Context) {
        new_course_data =data
        context =contxt
    }

    inner class NewCourseVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var course_thumbnail: RoundedImageView
        var validityTextTV: TextView
        var price: TextView
        var mrpCutTV: TextView
        var course_name: TextView
        var parent_layout: RelativeLayout


        init {
            course_thumbnail = itemView.findViewById(R.id.course_thumbnail)
            validityTextTV = itemView.findViewById(R.id.course_validty)
            parent_layout = itemView.findViewById(R.id.parent_layout)
            course_name = itemView.findViewById(R.id.course_name)
            mrpCutTV = itemView.findViewById(R.id.mrpCutTV)
            price = itemView.findViewById(R.id.priceTV)

        }
    }


}