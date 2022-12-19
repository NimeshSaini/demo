package com.utkarshnew.android.feeds.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.utkarshnew.android.Utils.Helper
import com.utkarshnew.android.databinding.BannerAdapterImageviewBinding
import com.utkarshnew.android.feeds.dataclass.BannerData

class Banner_ViewPager(private var context: Context?, private var list_banners: List<BannerData>?) :
    PagerAdapter() {
    override fun getCount(): Int {
        return if (list_banners != null && !list_banners!!.isEmpty()) {
            list_banners!!.size
        } else {
            0
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = BannerAdapterImageviewBinding.inflate(LayoutInflater.from(context))
        view.bannerviewadapter = this
        view.bannerdata = list_banners!![position]
        view.root.tag = position
        container.addView(view.root)
        return view.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

    override fun getPageWidth(position: Int): Float {
        return if (list_banners!!.size > 1) {
            0.90f
        } else 1f
    }

    fun clickBanner(target_meta: String) {
        try {
            if (!target_meta.equals("", ignoreCase = true)) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(target_meta))
                Helper.gotoActivity(browserIntent, context as Activity?)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    fun updateItems(data: List<BannerData>, ctx: Context?) {
        context = ctx
        list_banners = data
    }


}