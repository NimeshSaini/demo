package com.utkarshnew.android.feeds.dataclass

import NewTestResultAdapter
import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import androidx.room.TypeConverters
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.makeramen.roundedimageview.RoundedImageView
import com.utkarshnew.android.home.liveclasses.Datum
import com.utkarshnew.android.home.livetest.LiveTestData
import com.utkarshnew.android.R
import com.utkarshnew.android.feeds.adapters.*
import com.utkarshnew.android.table.PostDataTable
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class Data(
    var created: String = "",
    var id: String = "",
    var json: Json,
    var meta_url: String = "",
    var thumbnail: String = "",
    var url: String? = null,
    var modified: String = "",
    var my_like: String = "",
    var name: String = "",
    var post_type: String = "",
    var profile_picture: String = "",
    var status: String = "",
    var sub_cat_id: String = "",
    var text: String = "",
    var total_comments: String = "",
    var total_likes: String = "",
    var user_id: String = "",
    var newCourseData: List<NewCourseData>? = null,
    var livetest: List<LiveTestData>? = null,
    var liveclass: List<Datum>? = null,
    var testResult: List<TestResult>? = null,
    var bannerlist: List<BannerData>? = null,
    var is_comment_enable: String = "",
    var section_posiiton: String = "",
    var limit: Int = 0,
    var my_pinned: String = "",
    var description: String = "",
    var master_cat_id: String = "",
    var main_cat: String = "",
    var sub_cat: String = "",


    )


@BindingAdapter("imageUrl")
fun loadImage(view: CircleImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).apply(
            RequestOptions().placeholder(R.mipmap.square_placeholder)
                .error(R.mipmap.square_placeholder)
        ).into(view)
    }
}


@BindingAdapter("optionadapter")
fun setAdapter(recyclerView: RecyclerView, optionAdapter: OptionAdapter?) {
    optionAdapter?.let {
        recyclerView.adapter = optionAdapter
    }
}

@BindingAdapter("optionwebadapter")
fun setAdapter(recyclerView: RecyclerView, optionWebAdapter: OptionWebAdapter?) {
    optionWebAdapter?.let {
        recyclerView.adapter = optionWebAdapter
    }
}


@BindingAdapter("livetestresult")
fun setAdapter(recyclerView: RecyclerView, testResult: List<TestResult>) {

    if (recyclerView.adapter != null && recyclerView.adapter is NewTestResultAdapter) {
        (recyclerView.adapter as NewTestResultAdapter).updateItems(testResult, recyclerView.context)
    } else {
        val testResultAdapter = NewTestResultAdapter()
        recyclerView.adapter = testResultAdapter
        testResultAdapter.updateItems(testResult, recyclerView.context)
    }
}

@BindingAdapter("livetest")
fun setlivetestadapter(recyclerView: RecyclerView, liveTest: List<LiveTestData>) {
    if (recyclerView.adapter != null && recyclerView.adapter is NewLivetest) {
        (recyclerView.adapter as NewLivetest).updateItems(liveTest, recyclerView.context)
    } else {
        val livetest = NewLivetest()
        recyclerView.adapter = livetest
        livetest.updateItems(liveTest, recyclerView.context)
    }
}

@BindingAdapter("liveclass")
fun setliveclassadapter(recyclerView: RecyclerView, liveclasslist: List<Datum>) {
    if (recyclerView.adapter != null && recyclerView.adapter is NewLiveClass) {
        (recyclerView.adapter as NewLiveClass).updateItems(liveclasslist, recyclerView.context)
    } else {
        val liveclass = NewLiveClass()
        recyclerView.adapter = liveclass
        liveclass.updateItems(liveclasslist, recyclerView.context)
    }
}


@BindingAdapter("linkimage")
fun loadImage(view: RoundedImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(
                RequestOptions().placeholder(R.mipmap.square_placeholder)
                    .error(R.mipmap.square_placeholder)
            ).into(view)
    }
}

@BindingAdapter("likeview")
fun viewlike(view: TextView, mylike: String?) {
    if (mylike.equals("1")) {
        view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favorite, 0, 0, 0)
    } else {
        view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favorite_border, 0, 0, 0)
    }
}


@BindingAdapter("imageposturl")
fun imagepost(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).apply(
            RequestOptions().placeholder(R.mipmap.square_placeholder)
                .error(R.mipmap.square_placeholder)
        ).into(view)
    }
}


@BindingAdapter("imagefeedPost")
fun imagefeedPost(view: ImageView, data: Data?) {
    if (data?.meta_url!!.isNotEmpty()) {

        (view.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "16:9"

        data.json.view_type?.let {
            if (it == "2") {
                (view.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "9:16"
            } else if (it == "3") {
                (view.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "1:1"
            }
        }

        Glide.with(view.context).load(data.meta_url).diskCacheStrategy(DiskCacheStrategy.ALL).apply(
            RequestOptions().placeholder(R.mipmap.square_placeholder)
                .error(R.mipmap.square_placeholder)
        ).into(view)
    }
}

@BindingAdapter("imagedetailsfeedPost")
fun imagedetailsfeedPost(view: ImageView, data: PostDataTable?) {
    data?.let {
        if (it.meta_url.isNotEmpty()) {

            (view.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "16:9"

            val optiondata = Gson().fromJson(it.json, Json::class.java)

            optiondata.view_type?.let {
                if (it == "2") {
                    (view.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "9:16"
                } else if (it == "3") {
                    (view.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = "1:1"
                }
            }

            Glide.with(view.context).load(it.meta_url).diskCacheStrategy(DiskCacheStrategy.ALL).apply(
                RequestOptions().placeholder(R.mipmap.square_placeholder)
                    .error(R.mipmap.square_placeholder)
            ).into(view)
        }

    }
}


@SuppressLint("SimpleDateFormat")
@BindingAdapter("date")
fun loadImage(view: TextView, date: String?) {
    if (!date.isNullOrEmpty()) {
        view.text = SimpleDateFormat("dd MMM, yyyy hh:mm a").format(Date(date.toLong() * 1000))
    }
}