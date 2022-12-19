package com.utkarshnew.android.feeds.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.Helper
import com.utkarshnew.android.feeds.OptionItem
import com.utkarshnew.android.feeds.adapters.OptionAdapter.OptionHolder
import com.utkarshnew.android.feeds.dataclass.Json
import com.utkarshnew.android.feeds.dataclass.Option
import io.github.kexanie.library.MathView

class OptionAdapter(
    var context: Context,
    var optionList: List<Option>,
    var optionItem: OptionItem?,
    var feedlistPos: Int,
    var json: Json
) : RecyclerView.Adapter<OptionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_adapter, parent, false)
        return OptionHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: OptionHolder, position: Int) {

        val spannedHtml = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(optionList[position].option, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(optionList[position].option)
        }

        holder.option_txt.text =spannedHtml.trim()

        if (json.attempt_index.equals("0")) {
            holder.activeProgress.visibility =View.INVISIBLE
            holder.percentage_txt.visibility = View.GONE
            holder.option_a.setImageResource(R.drawable.defaultholo)
            holder.option_txt.setTextColor(context.getColor(R.color.color_8A000000))

        } else {
            holder.activeProgress.isVisible = true
            holder.percentage_txt.visibility = View.VISIBLE
            if (json.right_ans.isEmpty() || json.right_ans == "0") {
                holder.option_a.isVisible = false
                holder.my_ans.isVisible = false
                if (json.attempt_index == (position + 1).toString()) {
                    holder.my_ans.isVisible = true
                }
                val precentage = ((optionList[position].attempt_count.toFloat() / json.total_attempt!!.toFloat()) * 100).toInt()
                holder.percentage_txt.text = "${precentage}%"
                holder.activeProgress.progress = precentage
                holder.option_txt.setTextColor(context.getColor(R.color.black))
                holder.activeProgress.progressDrawable =
                    context.getDrawable(R.drawable.progress_poll)
            } else {
                holder.my_ans.isVisible = true
                if ((json.right_ans.equals(json.attempt_index)) && json.right_ans.toInt() == (position + 1)) {

                    val precentage =
                        ((optionList[position].attempt_count.toFloat() / json.total_attempt!!.toFloat()) * 100).toInt()
                    holder.percentage_txt.text = "${precentage}%"
                    holder.activeProgress.progress = precentage
                    holder.my_ans.setImageResource(R.drawable.correct)
                    holder.option_txt.setTextColor(context.getColor(R.color.black))
                    holder.activeProgress.progressDrawable =
                        context.getDrawable(R.drawable.progress_right)
                    holder.option_a.isVisible = false

                } else if ((json.attempt_index!!.toInt() == (position + 1))) {
                    val precentage =
                        ((optionList[position].attempt_count.toFloat() / json.total_attempt!!.toFloat()) * 100).toInt()
                    holder.activeProgress.progress = precentage
                    holder.percentage_txt.text = "${precentage}%"
                    holder.option_a.isVisible = false

                    holder.my_ans.setImageResource(R.drawable.wrong)
                    holder.option_txt.setTextColor(context.getColor(R.color.black))
                    holder.activeProgress.progressDrawable =
                        context.getDrawable(R.drawable.progress_wrong)
                } else if ((json.right_ans.toInt() == (position + 1))) {
                    val precentage =
                        ((optionList[position].attempt_count.toFloat() / json.total_attempt!!.toFloat()) * 100).toInt()
                    holder.percentage_txt.text = "${precentage}%"
                    holder.activeProgress.progress = precentage
                    holder.option_a.isVisible = false

                    holder.my_ans.setImageResource(R.drawable.correct)
                    holder.option_txt.setTextColor(context.getColor(R.color.black))
                    holder.activeProgress.progressDrawable =
                        context.getDrawable(R.drawable.progress_right)
                } else {
                    val precentage =
                        ((optionList[position].attempt_count.toFloat() / json.total_attempt!!.toFloat()) * 100).toInt()
                    holder.percentage_txt.text = "${precentage}%"
                    holder.activeProgress.progress = precentage
                    holder.option_a.isVisible = false
                    holder.my_ans.isVisible = false
                    holder.option_txt.setTextColor(context.getColor(R.color.black))
                    holder.activeProgress.progressDrawable = context.getDrawable(R.drawable.progress_default)
                }
            }


        }

        holder.layout_option.setOnClickListener {
            if (json.attempt_index == null || json.attempt_index.equals("0")) {
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
        var percentage_txt: TextView
        var layout_option: RelativeLayout
        var activeProgress: ProgressBar
        var my_ans: ImageView

        init {
            option_a = itemView.findViewById(R.id.option_a)
            option_txt = itemView.findViewById(R.id.option_txt)
            percentage_txt = itemView.findViewById(R.id.percentage_txt)
            layout_option = itemView.findViewById(R.id.layout_option)
            activeProgress = itemView.findViewById(R.id.activeProgress)
            my_ans = itemView.findViewById(R.id.my_ans)
        }
    }
}