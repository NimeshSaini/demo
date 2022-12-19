package com.utkarshnew.android.purchasehistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.utkarshnew.android.OnSingleClickListener
import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.Helper
import com.utkarshnew.android.Webview.WebViewActivty
import com.utkarshnew.android.databinding.ActivityPurchaseHistoryDetailBinding
import com.utkarshnew.android.purchasehistory.model.Data

class PurchaseHistoryDetail : AppCompatActivity() {

    var oderdata: Data? = null
    var activityPurchaseHistoryDetailBinding: ActivityPurchaseHistoryDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Helper.enableScreenShot(this@PurchaseHistoryDetail)
        activityPurchaseHistoryDetailBinding = DataBindingUtil.setContentView(this@PurchaseHistoryDetail, R.layout.activity_purchase_history_detail)
        with(intent)
        {
            oderdata = getSerializableExtra("data") as Data?
        }
        activityPurchaseHistoryDetailBinding?.apply {
            pruchasehistory = oderdata
        }

        activityPurchaseHistoryDetailBinding!!.imageBack.setOnClickListener(OnSingleClickListener {
            finish()
        })
        activityPurchaseHistoryDetailBinding!!.trackOrder.setOnClickListener {
            val intent =Intent(this,WebViewActivty::class.java)
            intent.putExtra("type","Track")
            intent.putExtra("url",oderdata!!.track_url_1)
            Helper.gotoActivity(intent,this)
        }
    }
}