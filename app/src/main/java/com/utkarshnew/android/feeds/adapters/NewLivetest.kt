package com.utkarshnew.android.feeds.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.home.livetest.LiveTestData
import com.utkarshnew.android.databinding.FeedLiveTestAdapterBinding
import java.text.SimpleDateFormat
import java.util.*

class NewLivetest : RecyclerView.Adapter<NewLivetest.NewCourseVH>() {
    var context: Context? =null
    var testResult: List<LiveTestData> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCourseVH {
        val view =FeedLiveTestAdapterBinding.inflate(LayoutInflater.from(context))
        return NewCourseVH(view)
    }
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: NewCourseVH, position: Int) {
        holder.bind(testResult[position])
    }
    override fun getItemCount(): Int {
        return testResult.size
    }
    inner class NewCourseVH(private  val feedLiveTestAdapterBinding: FeedLiveTestAdapterBinding) : RecyclerView.ViewHolder(feedLiveTestAdapterBinding.root) {
        fun bind(liveTestData: LiveTestData) {
            feedLiveTestAdapterBinding.livetestdata =liveTestData
        }
    }

    fun updateItems(liveTest: List<LiveTestData>, ctx: Context?) {
        context =ctx
        testResult =liveTest
    }


}