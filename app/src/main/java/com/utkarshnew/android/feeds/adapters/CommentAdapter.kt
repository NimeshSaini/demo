package com.utkarshnew.android.feeds.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.utkarshnew.android.feeds.dataclass.comment.Data

import com.utkarshnew.android.R
import com.utkarshnew.android.Utils.MakeMyExam
import com.utkarshnew.android.databinding.CommentAdapterBinding
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter(var context: Context, var optionList: List<Data>) : RecyclerView.Adapter<CommentAdapter.CommentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentVH {
        val view =CommentAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return CommentVH(view)
    }
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: CommentVH, position: Int) {
        holder.bind(optionList[position])

    }
    override fun getItemCount(): Int {
        return optionList.size
    }
    fun addToExistingList(feedatalist: List<Data>) {
        optionList= feedatalist
        notifyItemRangeChanged(optionList.size-1, feedatalist.size)
    }

    fun notifydata(commentlist: ArrayList<Data>) {
        optionList= commentlist
        notifyDataSetChanged()
    }

    inner class CommentVH(private  val commentAdapterBinding: CommentAdapterBinding) : RecyclerView.ViewHolder(commentAdapterBinding.root) {
        fun bind(data: Data) {
            commentAdapterBinding.commentdata =data
        }
    }


}