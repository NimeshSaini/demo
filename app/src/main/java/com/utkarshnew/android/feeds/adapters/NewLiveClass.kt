package com.utkarshnew.android.feeds.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.home.liveclasses.Datum
import com.utkarshnew.android.databinding.FeedLiveClassAdapterBinding
import java.text.SimpleDateFormat
import java.util.*

class NewLiveClass : RecyclerView.Adapter<NewLiveClass.NewCourseVH>() {

    var context: Context?=null
    var testclass: List<Datum> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCourseVH {

        val view = FeedLiveClassAdapterBinding.inflate( LayoutInflater.from(context))
        return NewCourseVH(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: NewCourseVH, position: Int) {
        holder.bind(testclass[position])
    }

    override fun getItemCount(): Int {
        return testclass.size
    }

    inner class NewCourseVH(private  val feedLiveClassAdapterBinding: FeedLiveClassAdapterBinding) : RecyclerView.ViewHolder(feedLiveClassAdapterBinding.root) {
        fun bind(datum: Datum) {
            feedLiveClassAdapterBinding.liveclassdata =datum
        }
    }
    fun updateItems(liveclass: List<Datum>, ctx: Context?) {
        testclass =liveclass
        context = ctx
    }

}