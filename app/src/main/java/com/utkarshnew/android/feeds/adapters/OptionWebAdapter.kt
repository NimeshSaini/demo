package com.utkarshnew.android.feeds.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.Helper
import com.utkarshnew.android.feeds.OptionItem
import com.utkarshnew.android.feeds.dataclass.Json
import com.utkarshnew.android.feeds.dataclass.Option
import io.github.kexanie.library.MathView

class OptionWebAdapter(
    var context: Context,
    var optionList: List<Option>,
    var optionItem: OptionItem?,
    var feedlistPos: Int,
    var json: Json
) : RecyclerView.Adapter<OptionWebAdapter.OptionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_web_adapter, parent, false)
        return OptionHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: OptionHolder, position: Int) {

        if (TextUtils.isEmpty(optionList[position].option)) {
            holder.layout_option.isVisible = false
        } else {
            holder.layout_option.isVisible = true
            if (optionList[position].option.contains("<img src=") || optionList[position].option.contains(
                    "math-tex"
                ) || optionList[position].option.contains("https://") || optionList[position].option.contains(
                    "http://"
                ) || optionList[position].option.contains("<img style")
            ) {
                holder.option_txt.visibility = View.GONE
                holder.optionTextTV.visibility = View.VISIBLE
                holder.optionTextTV.settings.javaScriptEnabled = true
                Helper.TestWebHTMLLoad(holder.optionTextTV, optionList[position].option)
            } else {
                holder.option_txt.setVisibility(View.VISIBLE)
                holder.optionTextTV.setVisibility(View.GONE)
                var option = optionList[position].option
                if (option.contains("&lt") || option.contains("&gt")) {
                    option = Html.fromHtml(option).toString()
                }

                val spannedHtml = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(option, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(option)
                }
                holder.option_txt.text =spannedHtml.trim()
            }
        }


        if (json.attempt_index.equals("0")) {
          //  holder.option_txt.setTextColor(context.getColor(R.color.color_8A000000))
            holder.option_a.setImageResource(R.drawable.defaultholo)
            holder.activeProgress.visibility = View.INVISIBLE

        } else {
            holder.activeProgress.visibility = View.VISIBLE
            if ((json.right_ans.equals(json.attempt_index)) && json.right_ans.toInt() == (position + 1)) {

               // holder.option_txt.setTextColor(context.getColor(R.color.color_8A000000))
                holder.activeProgress.progress = 100
                holder.option_a.setImageResource(R.drawable.correct)
                holder.activeProgress.progressDrawable =
                    context.getDrawable(R.drawable.progress_right)

            } else if ((json.attempt_index!!.toInt() == (position + 1))) {

             //   holder.option_txt.setTextColor(context.getColor(R.color.color_8A000000))
                holder.activeProgress.progress = 100
                holder.activeProgress.progressDrawable =
                    context.getDrawable(R.drawable.progress_wrong)
                holder.option_a.setImageResource(R.drawable.wrong)


            } else if ((json.right_ans.toInt() == (position + 1))) {
             //   holder.option_txt.setTextColor(context.getColor(R.color.color_8A000000))
                holder.activeProgress.progress = 100
                holder.option_a.setImageResource(R.drawable.correct)
                holder.activeProgress.progressDrawable =
                    context.getDrawable(R.drawable.progress_right)


            } else {
                holder.activeProgress.visibility = View.INVISIBLE
                holder.activeProgress.progress = 0
                holder.option_a.setImageResource(R.drawable.defaultholo)
            }
        }




        holder.layout_option.setOnClickListener {
            if (json.attempt_index == null || json.attempt_index.equals("") || json.attempt_index.equals(
                    "0"
                )
            ) {
                optionItem?.itemSelect(optionList[position], position, feedlistPos)
            } else {
                Toast.makeText(context, "Already Attempted", Toast.LENGTH_SHORT).show()
            }
        }

    }



    override fun getItemCount(): Int {
        return optionList.size
    }

    inner class OptionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var option_a: ImageView
        var option_txt: TextView
        var layout_option: RelativeLayout
        var optionTextTV: MathView
        var activeProgress: ProgressBar

        init {
            option_a = itemView.findViewById(R.id.option_a)
            option_txt = itemView.findViewById(R.id.optionTextTV2)
            layout_option = itemView.findViewById(R.id.layout_option)
            optionTextTV = itemView.findViewById(R.id.optionTextTV)
            activeProgress = itemView.findViewById(R.id.activeProgress)
        }
    }
}