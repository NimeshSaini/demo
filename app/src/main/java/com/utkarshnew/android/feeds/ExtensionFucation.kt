package com.utkarshnew.android.feeds

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.ahmadnemati.clickablewebview.ClickableWebView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.makeramen.roundedimageview.RoundedImageView
import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.Helper
import com.utkarshnew.android.Utils.MakeMyExam
import com.utkarshnew.android.feeds.adapters.Banner_ViewPager
import com.utkarshnew.android.feeds.adapters.NewCourseAdapter
import com.utkarshnew.android.feeds.dataclass.BannerData
import com.utkarshnew.android.feeds.dataclass.NewCourseData
import com.utkarshnew.android.feeds.dataclass.comment.Data
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@BindingAdapter("setwebview")
@SuppressLint("SetJavaScriptEnabled")
fun ClickableWebView.setwebview(message: String) {
    if (message.isNotEmpty()) {
        this.setBackgroundColor(Color.TRANSPARENT)
        this.setLayerType(WebView.LAYER_TYPE_HARDWARE, null)
        this.getSettings().setJavaScriptEnabled(true)
        this.getSettings().setGeolocationEnabled(true)
        Helper.TestWebHTMLLoad(this, message)
    }

}

@BindingAdapter("coursedata")
fun RecyclerView.courseadapter(data: List<NewCourseData>?) {
    if (data != null && data.size > 0) {
        if (adapter != null && adapter is NewCourseAdapter) {
            (adapter as NewCourseAdapter).updateItems(data, context)
        } else {
            adapter = NewCourseAdapter()
            (adapter as NewCourseAdapter).updateItems(data, context)
        }
    }


}

@BindingAdapter("viewpager")
fun ViewPager.load(data: List<BannerData>) {


    if (adapter != null && adapter is Banner_ViewPager) {
        (adapter as Banner_ViewPager).updateItems(data, context)
    } else {
        adapter = Banner_ViewPager(context, data)
    }

}


/*@BindingAdapter("imageposturl")
fun imagepost(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).apply(
            RequestOptions().placeholder(R.mipmap.square_placeholder)
                .error(R.mipmap.square_placeholder)
        ).into(view)
    }
}*/

@SuppressLint("SimpleDateFormat")
@BindingAdapter("datecomment")
fun TextView.date(date: String) {
    text = SimpleDateFormat("dd MMM, yyyy HH:mm a").format(Date(date.toLong() * 1000))
}

@BindingAdapter("attempts")
fun TextView.attempts(attempt: String?) {
    attempt?.toLong()?.let {
        this.text = "${formatNumber(it)} attempts"
    }
}

@BindingAdapter("like")
fun TextView.like(date: String?) {
    date?.toLong()?.let {
        this.text = "${formatNumber(it)} Likes"
    }
}

fun formatNumber(count: Long): String? {
    if (count < 1000) return "" + count
    val exp = (Math.log(count.toDouble()) / Math.log(1000.0)).toInt()
    return String.format(
        "%.1f %c", count / Math.pow(1000.0, exp.toDouble()), "kMGTPE"[exp - 1]
    )
}

@BindingAdapter("viewVisbile")
fun TextView.viewVisible(commentdata: Data) {
    if (commentdata.user_id.equals(MakeMyExam.userId) && commentdata.status.equals("0")) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE

    }
}


@BindingAdapter("coruseimage")
fun RoundedImageView.loadImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).apply(
            RequestOptions().placeholder(R.drawable.square_thumbnail)
                .error(R.drawable.square_thumbnail)
        ).into(this)
    }
}


@BindingAdapter("imageurl")
fun ImageView.imageurl(url: String) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
    }
}

@BindingAdapter("transactionstatus")
fun TextView.transactionstatus(status: String) {
    val transaction_status = when (status) {
        "0" -> "Pending"
        "1" -> "Complete"
        "2" -> "Cancel"
        "3" -> "Refund Req."
        "4" -> "Refunded"
        "5" -> "Declined"
        "6" -> "Transfered"
        "7" -> "Deleted"
        else -> "Processing"
    }
    text = transaction_status
}


